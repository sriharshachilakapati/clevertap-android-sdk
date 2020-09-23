package com.clevertap.android.sdk;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Custom ViewHolder for Simple message layout
 */
class CTSimpleMessageViewHolder extends CTInboxBaseMessageViewHolder {

    private TextView title;
    private TextView message;
    private TextView timestamp;
    private ImageView readDot;
    private Button cta1, cta2, cta3;

    CTSimpleMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setTag(this);
        title = itemView.findViewById(R.id.messageTitle);
        message = itemView.findViewById(R.id.messageText);
        timestamp = itemView.findViewById(R.id.timestamp);
        readDot = itemView.findViewById(R.id.read_circle);
        cta1 = itemView.findViewById(R.id.cta_button_1);
        cta2 = itemView.findViewById(R.id.cta_button_2);
        cta3 = itemView.findViewById(R.id.cta_button_3);
        mediaImage = itemView.findViewById(R.id.media_image);
        relativeLayout = itemView.findViewById(R.id.simple_message_relative_layout);
        frameLayout = itemView.findViewById(R.id.simple_message_frame_layout);
        squareImage = itemView.findViewById(R.id.square_media_image);
        clickLayout = itemView.findViewById(R.id.click_relative_layout);
        ctaLinearLayout = itemView.findViewById(R.id.cta_linear_layout);
        bodyRelativeLayout = itemView.findViewById(R.id.body_linear_layout);
        progressBarFrameLayout = itemView.findViewById(R.id.simple_progress_frame_layout);
        mediaLayout = itemView.findViewById(R.id.media_layout);

    }

    @Override
    void configureWithMessage(final CTInboxMessage inboxMessage, final CTInboxListViewFragment parent, final int position) {
        super.configureWithMessage(inboxMessage, parent, position);
        final CTInboxListViewFragment parentWeak = getParent();
        CTInboxMessageContent content = inboxMessage.getInboxMessageContents().get(0);

        this.title.setText(content.getTitle());
        this.title.setTextColor(Color.parseColor(content.getTitleColor()));
        this.message.setText(content.getMessage());
        this.message.setTextColor(Color.parseColor(content.getMessageColor()));
        this.bodyRelativeLayout.setBackgroundColor(Color.parseColor(inboxMessage.getBgColor()));
        String displayTimestamp = calculateDisplayTimestamp(inboxMessage.getDate());
        this.timestamp.setText(displayTimestamp);
        this.timestamp.setTextColor(Color.parseColor(content.getTitleColor()));
        if (inboxMessage.isRead()) {
            this.readDot.setVisibility(View.GONE);
        } else {
            this.readDot.setVisibility(View.VISIBLE);
        }
        frameLayout.setVisibility(View.GONE);
        //Shows the CTA layout only if links are present, also handles the display of the CTAs depending on the number
        JSONArray linksArray = content.getLinks();
        if (linksArray != null) {
            this.ctaLinearLayout.setVisibility(View.VISIBLE);
            int size = linksArray.length();
            JSONObject cta1Object, cta2Object, cta3Object;
            try {
                switch (size) {
                    case 1:
                        cta1Object = linksArray.getJSONObject(0);
                        this.cta1.setVisibility(View.VISIBLE);
                        this.cta1.setText(content.getLinkText(cta1Object));
                        this.cta1.setTextColor(Color.parseColor(content.getLinkColor(cta1Object)));
                        this.cta1.setBackgroundColor(Color.parseColor(content.getLinkBGColor(cta1Object)));
                        hideTwoButtons(this.cta1, this.cta2, this.cta3);
                        if (parentWeak != null) {
                            this.cta1.setOnClickListener(new CTInboxButtonClickListener(position, inboxMessage, this.cta1.getText().toString(), cta1Object, parentWeak));
                        }
                        break;
                    case 2:
                        cta1Object = linksArray.getJSONObject(0);
                        this.cta1.setVisibility(View.VISIBLE);
                        this.cta1.setText(content.getLinkText(cta1Object));
                        this.cta1.setTextColor(Color.parseColor(content.getLinkColor(cta1Object)));
                        this.cta1.setBackgroundColor(Color.parseColor(content.getLinkBGColor(cta1Object)));
                        cta2Object = linksArray.getJSONObject(1);
                        this.cta2.setVisibility(View.VISIBLE);
                        this.cta2.setText(content.getLinkText(cta2Object));
                        this.cta2.setTextColor(Color.parseColor(content.getLinkColor(cta2Object)));
                        this.cta2.setBackgroundColor(Color.parseColor(content.getLinkBGColor(cta2Object)));
                        hideOneButton(this.cta1, this.cta2, this.cta3);
                        if (parentWeak != null) {
                            this.cta1.setOnClickListener(new CTInboxButtonClickListener(position, inboxMessage, this.cta1.getText().toString(), cta1Object, parentWeak));
                            this.cta2.setOnClickListener(new CTInboxButtonClickListener(position, inboxMessage, this.cta2.getText().toString(), cta2Object, parentWeak));
                        }
                        break;
                    case 3:
                        cta1Object = linksArray.getJSONObject(0);
                        this.cta1.setVisibility(View.VISIBLE);
                        this.cta1.setText(content.getLinkText(cta1Object));
                        this.cta1.setTextColor(Color.parseColor(content.getLinkColor(cta1Object)));
                        this.cta1.setBackgroundColor(Color.parseColor(content.getLinkBGColor(cta1Object)));
                        cta2Object = linksArray.getJSONObject(1);
                        this.cta2.setVisibility(View.VISIBLE);
                        this.cta2.setText(content.getLinkText(cta2Object));
                        this.cta2.setTextColor(Color.parseColor(content.getLinkColor(cta2Object)));
                        this.cta2.setBackgroundColor(Color.parseColor(content.getLinkBGColor(cta2Object)));
                        cta3Object = linksArray.getJSONObject(2);
                        this.cta3.setVisibility(View.VISIBLE);
                        this.cta3.setText(content.getLinkText(cta3Object));
                        this.cta3.setTextColor(Color.parseColor(content.getLinkColor(cta3Object)));
                        this.cta3.setBackgroundColor(Color.parseColor(content.getLinkBGColor(cta3Object)));
                        if (parentWeak != null) {
                            this.cta1.setOnClickListener(new CTInboxButtonClickListener(position, inboxMessage, this.cta1.getText().toString(), cta1Object, parentWeak));
                            this.cta2.setOnClickListener(new CTInboxButtonClickListener(position, inboxMessage, this.cta2.getText().toString(), cta2Object, parentWeak));
                            this.cta3.setOnClickListener(new CTInboxButtonClickListener(position, inboxMessage, this.cta3.getText().toString(), cta3Object, parentWeak));
                        }
                        break;
                }
            } catch (JSONException e) {
                Logger.d("Error parsing CTA JSON - " + e.getLocalizedMessage());
            }
        } else {
            this.ctaLinearLayout.setVisibility(View.GONE);
        }
        this.mediaImage.setVisibility(View.GONE);
        this.mediaImage.setBackgroundColor(Color.parseColor(inboxMessage.getBgColor()));
        this.squareImage.setVisibility(View.GONE);
        this.squareImage.setBackgroundColor(Color.parseColor(inboxMessage.getBgColor()));
        this.mediaLayout.setVisibility(View.GONE);
        this.progressBarFrameLayout.setVisibility(View.GONE);
        try {
            switch (inboxMessage.getOrientation()) {
                case "l":
                    if (content.mediaIsImage()) {
                        this.mediaLayout.setVisibility(View.VISIBLE);
                        this.mediaImage.setVisibility(View.VISIBLE);
                        this.mediaImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        try {
                            Glide.with(this.mediaImage.getContext())
                                    .load(content.getMedia())
                                    .apply(new RequestOptions()
                                            .placeholder(Utils.getThumbnailImage(context, Constants.IMAGE_PLACEHOLDER))
                                            .error(Utils.getThumbnailImage(context, Constants.IMAGE_PLACEHOLDER)))
                                    .into(this.mediaImage);
                        } catch (NoSuchMethodError error) {
                            Logger.d("CleverTap SDK requires Glide v4.9.0 or above. Please refer CleverTap Documentation for more info");
                            Glide.with(this.mediaImage.getContext())
                                    .load(content.getMedia())
                                    .into(this.mediaImage);
                        }
                    } else if (content.mediaIsGIF()) {
                        this.mediaLayout.setVisibility(View.VISIBLE);
                        this.mediaImage.setVisibility(View.VISIBLE);
                        this.mediaImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        try {
                            Glide.with(this.mediaImage.getContext())
                                    .asGif()
                                    .load(content.getMedia())
                                    .apply(new RequestOptions()
                                            .placeholder(Utils.getThumbnailImage(context, Constants.IMAGE_PLACEHOLDER))
                                            .error(Utils.getThumbnailImage(context, Constants.IMAGE_PLACEHOLDER)))
                                    .into(this.mediaImage);
                        } catch (NoSuchMethodError error) {
                            Logger.d("CleverTap SDK requires Glide v4.9.0 or above. Please refer CleverTap Documentation for more info");
                            Glide.with(this.mediaImage.getContext())
                                    .asGif()
                                    .load(content.getMedia())
                                    .into(this.mediaImage);
                        }

                    } else if (content.mediaIsVideo()) {
                        if (!content.getPosterUrl().isEmpty()) {
                            this.mediaLayout.setVisibility(View.VISIBLE);
                            this.mediaImage.setVisibility(View.VISIBLE);
                            this.mediaImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            try {
                                Glide.with(this.mediaImage.getContext())
                                        .load(content.getPosterUrl())
                                        .apply(new RequestOptions()
                                                .placeholder(Utils.getThumbnailImage(context, Constants.VIDEO_THUMBNAIL))
                                                .error(Utils.getThumbnailImage(context, Constants.VIDEO_THUMBNAIL)))
                                        .into(this.mediaImage);
                            } catch (NoSuchMethodError error) {
                                Logger.d("CleverTap SDK requires Glide v4.9.0 or above. Please refer CleverTap Documentation for more info");
                                Glide.with(this.mediaImage.getContext())
                                        .load(content.getPosterUrl())
                                        .into(this.mediaImage);
                            }

                        } else {
                            this.mediaLayout.setVisibility(View.VISIBLE);
                            this.mediaImage.setVisibility(View.VISIBLE);
                            this.mediaImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            int drawableId = Utils.getThumbnailImage(context, Constants.VIDEO_THUMBNAIL);
                            if (drawableId != -1) {
                                Glide.with(this.mediaImage.getContext())
                                        .load(drawableId)
                                        .into(this.mediaImage);
                            }
                        }
                    } else if (content.mediaIsAudio()) {
                        this.mediaLayout.setVisibility(View.VISIBLE);
                        this.mediaImage.setVisibility(View.VISIBLE);
                        this.mediaImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        this.mediaImage.setBackgroundColor(getImageBackgroundColor());
                        int drawableId = Utils.getThumbnailImage(context, Constants.AUDIO_THUMBNAIL);
                        if (drawableId != -1) {
                            Glide.with(this.mediaImage.getContext())
                                    .load(drawableId)
                                    .into(this.mediaImage);
                        }
                    }
                    break;
                case "p":
                    if (content.mediaIsImage()) {
                        this.mediaLayout.setVisibility(View.VISIBLE);
                        this.squareImage.setVisibility(View.VISIBLE);
                        this.squareImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        try {
                            Glide.with(this.squareImage.getContext())
                                    .load(content.getMedia())
                                    .apply(new RequestOptions()
                                            .placeholder(Utils.getThumbnailImage(context, Constants.IMAGE_PLACEHOLDER))
                                            .error(Utils.getThumbnailImage(context, Constants.IMAGE_PLACEHOLDER)))
                                    .into(this.squareImage);
                        } catch (NoSuchMethodError error) {
                            Logger.d("CleverTap SDK requires Glide v4.9.0 or above. Please refer CleverTap Documentation for more info");
                            Glide.with(this.squareImage.getContext())
                                    .load(content.getMedia())
                                    .into(this.squareImage);
                        }
                    } else if (content.mediaIsGIF()) {
                        this.mediaLayout.setVisibility(View.VISIBLE);
                        this.squareImage.setVisibility(View.VISIBLE);
                        this.squareImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        try {
                            Glide.with(this.squareImage.getContext())
                                    .asGif()
                                    .load(content.getMedia())
                                    .apply(new RequestOptions()
                                            .placeholder(Utils.getThumbnailImage(context, Constants.IMAGE_PLACEHOLDER))
                                            .error(Utils.getThumbnailImage(context, Constants.IMAGE_PLACEHOLDER)))
                                    .into(this.squareImage);
                        } catch (NoSuchMethodError error) {
                            Logger.d("CleverTap SDK requires Glide v4.9.0 or above. Please refer CleverTap Documentation for more info");
                            Glide.with(this.squareImage.getContext())
                                    .asGif()
                                    .load(content.getMedia())
                                    .into(this.squareImage);
                        }
                    } else if (content.mediaIsVideo()) {
                        if (!content.getPosterUrl().isEmpty()) {
                            this.mediaLayout.setVisibility(View.VISIBLE);
                            this.squareImage.setVisibility(View.VISIBLE);
                            if (CTInboxActivity.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                this.squareImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            } else {
                                this.squareImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            }
                            try {
                                Glide.with(this.squareImage.getContext())
                                        .load(content.getPosterUrl())
                                        .apply(new RequestOptions()
                                                .placeholder(Utils.getThumbnailImage(context, Constants.VIDEO_THUMBNAIL))
                                                .error(Utils.getThumbnailImage(context, Constants.VIDEO_THUMBNAIL)))
                                        .into(this.squareImage);
                            } catch (NoSuchMethodError error) {
                                Logger.d("CleverTap SDK requires Glide v4.9.0 or above. Please refer CleverTap Documentation for more info");
                                Glide.with(this.squareImage.getContext())
                                        .load(content.getPosterUrl())
                                        .into(this.squareImage);
                            }

                        } else {
                            this.mediaLayout.setVisibility(View.VISIBLE);
                            this.squareImage.setVisibility(View.VISIBLE);
                            if (CTInboxActivity.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                this.squareImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            } else {
                                this.squareImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            }
                            int drawableId = Utils.getThumbnailImage(context, Constants.VIDEO_THUMBNAIL);
                            if (drawableId != -1) {
                                Glide.with(this.squareImage.getContext())
                                        .load(drawableId)
                                        .into(this.squareImage);
                            }
                        }
                    } else if (content.mediaIsAudio()) {
                        this.mediaLayout.setVisibility(View.VISIBLE);
                        this.squareImage.setVisibility(View.VISIBLE);
                        this.squareImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        this.squareImage.setBackgroundColor(getImageBackgroundColor());
                        int drawableId = Utils.getThumbnailImage(context, Constants.AUDIO_THUMBNAIL);
                        if (drawableId != -1) {
                            Glide.with(this.squareImage.getContext())
                                    .load(drawableId)
                                    .into(this.squareImage);
                        }
                    }
                    break;
            }
        } catch (NoClassDefFoundError error) {
            Logger.d("CleverTap SDK requires Glide dependency. Please refer CleverTap Documentation for more info");
        }
        //Set the height and width of Progress Bar Frame to match the thumbnail size
        final Resources resources = context.getResources();
        int width;
        int height;
        if (CTInboxActivity.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            height = resources.getDisplayMetrics().heightPixels / 2;
            width = resources.getDisplayMetrics().widthPixels / 2;
        } else {
            width = resources.getDisplayMetrics().widthPixels;
            height = inboxMessage.getOrientation().equalsIgnoreCase("l") ? Math.round(width * 0.5625f) : width;
        }

        this.progressBarFrameLayout.setLayoutParams(new RelativeLayout.LayoutParams(width, height));

        //New thread to remove the Read dot, mark message as read and raise Notification Viewed
        Runnable simpleRunnable = new Runnable() {
            @Override
            public void run() {
                final CTInboxListViewFragment parent = getParent();
                if (parent != null) {
                    Activity activity = parent.getActivity();
                    if (activity == null) return;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (readDot.getVisibility() == View.VISIBLE) {
                                parent.didShow(null, position);
                            }
                            readDot.setVisibility(View.GONE);
                        }
                    });
                }

            }
        };
        Handler simpleHandler = new Handler();
        simpleHandler.postDelayed(simpleRunnable, 2000);
        if (parentWeak != null) {
            this.clickLayout.setOnClickListener(new CTInboxButtonClickListener(position, inboxMessage, null, null, parentWeak));
        }

    }
}
