package com.gusi.androidx.module.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gusi.androidx.R;
import com.gusi.androidx.model.entity.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ylw
 * @since 2019/7/23 22:53
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private List<Project> mProjects = new ArrayList<>(10);
    private LayoutInflater mInflater;

    public ProjectAdapter(LayoutInflater inflater) {
        mInflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Project project = mProjects.get(position);
        holder.mTvProjectName.setText(project.getProjectName());
        holder.mTvReceiveSite.setText(project.getReceiveSite());
        holder.mTvRegisterTime.setText(project.getRegisterTime());
    }

    @Override
    public int getItemCount() {
        return mProjects == null ? 0 : mProjects.size();
    }

    public void setProjects(List<Project> projects) {
        mProjects = projects;
    }

    public List<Project> getProjects() {
        return mProjects;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvProjectName;
        private final TextView mTvReceiveSite;
        private final TextView mTvRegisterTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvProjectName = itemView.findViewById(R.id.tv_project_name);
            mTvReceiveSite = itemView.findViewById(R.id.tv_receive_site);
            mTvRegisterTime = itemView.findViewById(R.id.tv_register_time);
        }
    }
}