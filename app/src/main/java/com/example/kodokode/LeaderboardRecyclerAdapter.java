package com.example.kodokode;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class LeaderboardRecyclerAdapter extends RecyclerView.Adapter<LeaderboardRecyclerAdapter.ViewHolder> {

    private static final String TAG = "LBRecyclerAdapter";

    private List<User> rankedUsersList;
    private User currentUser;
    private boolean isCurrentUser;

    public LeaderboardRecyclerAdapter(List<User> rankedUsersList, User currentUser) {
        this.rankedUsersList = rankedUsersList;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public LeaderboardRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_leaderboard, parent, false);
        LeaderboardRecyclerAdapter.ViewHolder holder = new LeaderboardRecyclerAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardRecyclerAdapter.ViewHolder viewHolder, int position) {
        User viewHolderUser = rankedUsersList.get(position);
        // check if current view holder is for current user
        isCurrentUser = viewHolderUser.getUsername().equals(currentUser.getUsername());
        viewHolder.rankUser(viewHolderUser, position);
    }

    @Override
    public int getItemCount() {
        return rankedUsersList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView position;
        ImageView medalImage;
        TextView username;
        TextView userPoints;
        LinearLayout parentLayout;

        private ViewHolder(View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.position);
            medalImage = itemView.findViewById(R.id.medal_image);
            username = itemView.findViewById(R.id.username_heading);
            userPoints = itemView.findViewById(R.id.points_heading);
            parentLayout = itemView.findViewById(R.id.parent_layout_leaderboard);
        }

        // retrieves the user of the current position and sets the username and points of the user
        private void rankUser(User user, int position) {
            int newPosition = position + 1;
            this.position.setText(newPosition + "");
            this.username.setText(user.getUsername());
            this.userPoints.setText(user.getPoints() + "");

            if (isCurrentUser) {
                parentLayout.setBackgroundResource(R.drawable.rectangle_accent);
                // change background color to color accent if is current user
            }

            // set image of medal to display depending on the position
            // if it is not any of these 3, the default medal image is used
            if (newPosition == 1) {
                medalImage.setImageResource(R.drawable.medal_first);
            }
            if (newPosition == 2) {
                medalImage.setImageResource(R.drawable.medal_second);
            }
            if (newPosition == 3) {
                medalImage.setImageResource(R.drawable.medal_third);
            }
        }

    }
}
