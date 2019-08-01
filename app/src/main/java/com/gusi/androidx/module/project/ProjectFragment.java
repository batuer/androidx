package com.gusi.androidx.module.project;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gusi.androidx.R;
import com.gusi.androidx.base.BaseFragment;
import com.gusi.androidx.model.entity.Project;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * @author ylw 2019/7/21 22:01
 * @Des
 */
public class ProjectFragment extends BaseFragment<ProjectPresenter> implements ProjectContract.View {
    public static final String PRE_URL = "PreUrl";

    @BindView(R.id.rcv_project)
    RecyclerView mRcvProject;
    @BindView(R.id.refresh)
    TwinklingRefreshLayout mRefreshLayout;
    private int mPage = 0;
    private String mPreUrl;
    private ProjectAdapter mProjectAdapter;

    private ProjectFragment() {}

    public static ProjectFragment newInstance(String preUrl) {
        Bundle args = new Bundle();
        args.putString(PRE_URL, preUrl);
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initView() {
        mRcvProject.setLayoutManager(new LinearLayoutManager(getActivity()));
        mProjectAdapter = new ProjectAdapter(LayoutInflater.from(getContext()));
        mRcvProject.setHasFixedSize(true);
        mRcvProject.setAdapter(mProjectAdapter);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (mPreUrl != null) {
                    mPresenter.getProjects(mPreUrl + 1, true);
                }
                mRefreshLayout.finishRefreshing();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                if (mPreUrl != null) {
                    mPresenter.getProjects(mPreUrl + (mPage + 1), false);
                }
                mRefreshLayout.finishLoadmore();
            }
        });
    }

    @Override
    public void showProjects(List<Project> projects, boolean isRefresh) {
        if (mRefreshLayout.isLoadMore()) {
            mRefreshLayout.finishLoadmore();
        }
        if (mRefreshLayout.isRefrshing()) {
            mRefreshLayout.finishRefreshing();
        }
        if (projects.isEmpty()) {
            showInfo("没有数据了~");
            return;
        }
        List<Project> list = mProjectAdapter.getProjects();
        if (isRefresh) {
            mPage = 1;
            list.clear();
        } else {
            mPage += 1;
        }
        list.addAll(projects);
        mProjectAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {
        super.initData();
        mPreUrl = getArguments().getString(PRE_URL);
        if (mPreUrl != null) {
            mPresenter.getProjects(mPreUrl + 1, false);
        }
    }
}
