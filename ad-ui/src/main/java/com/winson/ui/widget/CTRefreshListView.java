package com.winson.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.winson.ui.R;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class CTRefreshListView extends ListView {

	private static final int RATIO = 3;

	private View mHeaderView;// 表头下拉
	private ProgressBar pb_refresh;
	private ImageView iv_arrow;
	private TextView tv_title;
	private TextView tv_time;
	private Animation animation;
	private Animation reverseAnimation;

	private int startY;// 下拉位置
	private int mHeaderHeight;// 表头高度

	public enum State {// 下拉状态
		ORIGNAL, PULL_TO_REFRESH, REFRESHING, RELEASE_TO_REFRESH;
	}

	private State mState;// 下拉状态
	private boolean isScroolIdle;// 滚动状态
	private boolean isCanHeaderRefresh = true;// 能否下拉刷新
	private boolean isCanFooterRefresh = true;// 能否滚动加载

	private boolean isLoading;// 是否正在加载
	private boolean isHeaderRefreshEnable = true;
	private boolean isFooterRefreshEnable = true;

	private View mFooterView;// 表尾加载

	private OnScrollListener mOnScrollListener;// 滚动监听器
	private OnHeaderRefreshListener mOnHeaderRefreshListener;// 下拉监听器
	private OnFooterRefreshListener mOnFooterRefreshListener;// 加载监听器

	public CTRefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public CTRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public CTRefreshListView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {// 页面初始化
		// 正反动画
		animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(250);
		animation.setFillAfter(true);
		animation.setInterpolator(new LinearInterpolator());

		reverseAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);
		reverseAnimation.setInterpolator(new LinearInterpolator());

		// HeaderView
		mHeaderView = View.inflate(context, R.layout.refresh_header, null);
		pb_refresh = (ProgressBar) mHeaderView.findViewById(R.id.pb_refresh);
		iv_arrow = (ImageView) mHeaderView.findViewById(R.id.iv_arrow);
		tv_title = (TextView) mHeaderView.findViewById(R.id.tv_title);
		tv_time = (TextView) mHeaderView.findViewById(R.id.tv_time);
		measureHeaderView(mHeaderView);
		mHeaderView.invalidate();
		mHeaderHeight = mHeaderView.getMeasuredHeight();
		changeHeaderState(State.ORIGNAL);
		addHeaderView(mHeaderView);

		// FooterView
		mFooterView = View.inflate(context, R.layout.refresh_footer, null);
		measureHeaderView(mFooterView);
		mFooterView.invalidate();
		showFooterView(false);
		addFooterView(mFooterView);

		setFooterDividersEnabled(false);

		super.setOnScrollListener(superOnScrollListener);
	}

	private void measureHeaderView(View view) {// 测量表头高度
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		if (lp == null) {
			lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
		}
		int childMeasureWidth = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
		int childMeasureHeight;
		if (lp.height > 0) {
			childMeasureHeight = MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.EXACTLY);
		} else {
			childMeasureHeight = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		view.measure(childMeasureWidth, childMeasureHeight);
	}

	public void changeHeaderState(State state) {// 下拉状态切换
		if (mState == state) {
			return;
		} else {
			mState = state;
		}
		switch (mState) {
		case ORIGNAL:
			iv_arrow.setVisibility(View.VISIBLE);
			pb_refresh.setVisibility(View.GONE);

			tv_title.setText("下拉刷新");
			iv_arrow.clearAnimation();
			mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
			break;
		case PULL_TO_REFRESH:
			tv_title.setText("下拉刷新");
			iv_arrow.clearAnimation();
			iv_arrow.startAnimation(animation);
			break;
		case RELEASE_TO_REFRESH:
			tv_title.setText("松开刷新");
			iv_arrow.clearAnimation();
			iv_arrow.startAnimation(reverseAnimation);
			break;
		case REFRESHING:
			iv_arrow.setVisibility(View.GONE);
			pb_refresh.setVisibility(View.VISIBLE);

			tv_title.setText("正在刷新...");
			iv_arrow.clearAnimation();
			mHeaderView.setPadding(0, 0, 0, 0);
			if (mOnHeaderRefreshListener != null) {
				isLoading = true;
				mOnHeaderRefreshListener.OnHeaderRefresh();
			} else {
				onHeaderRefreshComplete();
			}
			break;
		default:
			break;
		}
	}

	private void showHeaderView(boolean flag) {
		if (flag) {
			mHeaderView.setVisibility(View.VISIBLE);
			mHeaderView.setPadding(0, 0, 0, 0);
		} else {
			mHeaderView.setVisibility(View.GONE);
			mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
		}
	}

	private void showFooterView(boolean flag) {
		if (flag) {
			mFooterView.setVisibility(View.VISIBLE);
			mFooterView.setPadding(0, 0, 0, 0);
		} else {
			mFooterView.setVisibility(View.GONE);
			mFooterView.setPadding(0, -mFooterView.getMeasuredHeight(), 0, 0);
		}
	}
	
	// @Override
	// public boolean dispatchTouchEvent(MotionEvent ev) {
	// // TODO Auto-generated method stub
	// onTouchEvent(ev);
	// return true;
	// }
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (isLoading) {
			return super.onInterceptTouchEvent(event);
		}
		int currentY = (int) event.getRawY();
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_POINTER_DOWN:
			startY = getMeasuredHeight();
			break;
		case MotionEvent.ACTION_DOWN:
			startY = currentY;
			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

			break;
		default:
			break;
		}
		return super.onInterceptTouchEvent(event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {// 下拉事件
		if (isLoading) {
			return super.onTouchEvent(event);
		}
		int currentY = (int) event.getRawY();
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		// case MotionEvent.ACTION_POINTER_DOWN:
		// startY = getMeasuredHeight();
		// break;
		// case MotionEvent.ACTION_DOWN:
		// startY = currentY;
		// break;
		case MotionEvent.ACTION_MOVE:
			int offsetY = (currentY - startY) / RATIO;
			int offset = offsetY - mHeaderHeight;
			// System.out.println("offsetY---->" + offsetY);
			// System.out.println("CanRefresh---->" + isCanHeaderRefresh + "," +
			// isCanFooterRefresh);
			if (isHeaderRefreshEnable && isCanHeaderRefresh) {
				if (offsetY <= 0) {
					mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
					changeHeaderState(State.ORIGNAL);
				}
				if (offsetY > 0 && offsetY <= mHeaderHeight) {
					mHeaderView.setPadding(0, offset, 0, 0);
					changeHeaderState(State.PULL_TO_REFRESH);

				}
				if (offsetY > mHeaderHeight) {
					mHeaderView.setPadding(0, offset, 0, 0);
					if (mState == State.RELEASE_TO_REFRESH) {
						return true;
					} else {
						changeHeaderState(State.RELEASE_TO_REFRESH);
					}
				}
			}
			if (isFooterRefreshEnable && isCanFooterRefresh) {
				mFooterView.setPadding(0, offset, 0, 0);
			}

			break;
		case MotionEvent.ACTION_UP:
			switch (mState) {
			case PULL_TO_REFRESH:
				changeHeaderState(State.ORIGNAL);
				break;
			case RELEASE_TO_REFRESH:
				changeHeaderState(State.REFRESHING);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	private OnScrollListener superOnScrollListener = new OnScrollListener() {// 滚动加载

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (mOnScrollListener != null) {
				mOnScrollListener.onScrollStateChanged(view, scrollState);
			}
			isScroolIdle = (scrollState == SCROLL_STATE_IDLE);
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
				int totalItemCount) {

			if (mOnScrollListener != null) {
				mOnScrollListener
						.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
			if (firstVisibleItem == 0) {
				isCanHeaderRefresh = true;
			} else {
				isCanHeaderRefresh = false;
			}

			if ((firstVisibleItem + visibleItemCount >= totalItemCount)) {
				isCanFooterRefresh = true;
			} else {
				isCanFooterRefresh = false;
			}
			// 加载
			if (!isFooterRefreshEnable || isLoading) {
				return;
			}
			if (visibleItemCount == totalItemCount) {
				showFooterView(false);
			} else if (isCanFooterRefresh && !isScroolIdle) {
				isLoading = true;
				showFooterView(true);
				if (mOnFooterRefreshListener != null) {
					mOnFooterRefreshListener.OnFooterRefresh();
				} else {
					onFooterRefreshComplete();
				}
			}
		}
	};

	// 接口
	public void setHeaderRefreshEnable(boolean flag) {// 下拉使能
		isHeaderRefreshEnable = flag;
		//showHeaderView(flag);
	}

	public void setFooterRefreshEnable(boolean flag) {// 加载使能
		isFooterRefreshEnable = flag;
		showFooterView(flag);
	}

	public void onHeaderRefreshComplete() {// 下拉完成
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tv_time.setText("更新时间：" + sdf.format(new Date()));
		changeHeaderState(State.ORIGNAL);
		isLoading = false;
	}

	public void onFooterRefreshComplete() {// 加载完成
		showFooterView(false);
		isLoading = false;
	}

	public interface OnHeaderRefreshListener {
		abstract void OnHeaderRefresh();
	}

	public interface OnFooterRefreshListener {
		abstract void OnFooterRefresh();
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		onHeaderRefreshComplete();
		super.setAdapter(adapter);
	}

	@Override
	public void setOnScrollListener(OnScrollListener listener) {
		this.mOnScrollListener = listener;
	}

	public void setOnHeaderRefreshListener(OnHeaderRefreshListener listener) {// 设置下拉监听
		this.mOnHeaderRefreshListener = listener;
	}

	public void setOnFooterRefreshListener(OnFooterRefreshListener listener) {// 设置加载监听
		mOnFooterRefreshListener = listener;
	}
}
