package com.wclepu.demo.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wclepu.demo.R;
import com.wclepu.demo.adapter.PageAdapterTab;
import com.wclepu.demo.refreash.PullToRefreshBase;
import com.wclepu.demo.refreash.PullToRefreshListView;


public class Tab3ListFragment extends ScrollTabHolderFragment {

	private PullToRefreshListView listView;

	private View placeHolderView;

	private ArrayAdapter<String> adapter;

	private ArrayList<String> listItems;

	private Handler handler;

	public Tab3ListFragment() {
		this.setFragmentId(PageAdapterTab.PAGE_TAB3.fragmentId);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.page_tab_fragment_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		findViews();
		initListView();
	}

	@SuppressLint("InflateParams")
	private void findViews() {
		handler = new Handler(Looper.getMainLooper());
		listView = (PullToRefreshListView) getView().findViewById(R.id.page_tab_listview);
	}

	private void initListView() {
		//setListViewListener();
		listViewAddHeader();
		//listViewLoadData();
	}

	private void setListViewListener() {
		listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				loadNews();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				loadOlds();
			}

		});

		listView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (scrollTabHolder != null) {
					scrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, getFragmentId());
				}
			}
		});

		listView.setOnHeaderScrollListener(new PullToRefreshListView.OnHeaderScrollListener() {

			@Override
			public void onHeaderScroll(boolean isRefreashing, boolean istop, int value) {
				if (scrollTabHolder != null && istop) {
					scrollTabHolder.onHeaderScroll(isRefreashing, value, getFragmentId());
				}
			}
		});
	}

	private void listViewAddHeader() {
		placeHolderView = new LinearLayout(getActivity());
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, getResources()
				.getDimensionPixelSize(R.dimen.max_header_height));
		placeHolderView.setLayoutParams(params);
		listView.getRefreshableView().addHeaderView(placeHolderView);
	}

	protected void listViewLoadData() {
		listItems = new ArrayList<String>();
		for (int i = 1; i <= 50; i++) {
			listItems.add("currnet page: " + (getFragmentId() + 1) + " item --" + i);
		}
		adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, android.R.id.text1, listItems);
		listView.setAdapter(adapter);
		loadNews();
	}

	/**
	 * ������վɵ�����
	 */
	private void loadNews() {
		handler.postDelayed(new Runnable() {// ģ��Զ�̻�ȡ����

					@Override
					public void run() {
						stopRefresh();
						// listItems.clear();
						// for (int i = 1; i <= 50; i++) {
						// listItems.add("currnet page: " + (getFragmentId() +
						// 1) + " item --" + i);
						// }
						// notifyAdpterdataChanged();
					}
				}, 300);
	}

	private void notifyAdpterdataChanged() {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	protected void loadOlds() {
		handler.postDelayed(new Runnable() {// ģ��Զ�̻�ȡ����

					@Override
					public void run() {
						stopRefresh();
						int size = listItems.size() + 1;
						for (int i = size; i < size + 50; ++i) {
							listItems.add("currnet page: " + (getFragmentId() + 1) + " item --" + i);
						}
						notifyAdpterdataChanged();
					}
				}, 300);
	}

	// PullToRefreshListView �Զ������һ��ͷ��
	@Override
	public void adjustScroll(int scrollHeight) {
		if (scrollHeight == 0 && listView.getRefreshableView().getFirstVisiblePosition() >= 2) {
			return;
		}
		listView.getRefreshableView().setSelectionFromTop(2, scrollHeight);

	}

	protected void updateListView() {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	protected void stopRefresh() {
		listView.onRefreshComplete();
	}
}