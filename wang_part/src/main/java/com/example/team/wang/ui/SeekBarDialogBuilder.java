package com.example.team.wang.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.team.wang_part.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogBuilder;
import com.qmuiteam.qmui.widget.textview.QMUISpanTouchFixTextView;
import com.xw.repo.BubbleSeekBar;

/**
 * Created by Ellly on 2018/2/21.
 */

public class SeekBarDialogBuilder extends QMUIDialogBuilder<SeekBarDialogBuilder> {

    private RelativeLayout mMainLayout;
    private BubbleSeekBar mSeekBar;
    private TextView mTimeTv;
    private int mSeekProgress = 50;

    public SeekBarDialogBuilder(Context context) {
        super(context);

        mTimeTv = new QMUISpanTouchFixTextView(mContext);
        mTimeTv.setTextColor(QMUIResHelper.getAttrColor(mContext, com.qmuiteam.qmui.R.attr.qmui_config_color_gray_4));
        mTimeTv.setLineSpacing(QMUIDisplayHelper.dpToPx(2), 1.0f);
        mTimeTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, QMUIResHelper.getAttrDimen(mContext, com.qmuiteam.qmui.R.attr.qmui_dialog_content_message_text_size));

        mTimeTv.setPadding(
                QMUIResHelper.getAttrDimen(mContext, com.qmuiteam.qmui.R.attr.qmui_dialog_padding_horizontal),
                QMUIResHelper.getAttrDimen(mContext, hasTitle() ? com.qmuiteam.qmui.R.attr.qmui_dialog_content_padding_top : com.qmuiteam.qmui.R.attr.qmui_dialog_content_padding_top_when_no_title),
                QMUIResHelper.getAttrDimen(mContext, com.qmuiteam.qmui.R.attr.qmui_dialog_padding_horizontal),
                QMUIResHelper.getAttrDimen(mContext, com.qmuiteam.qmui.R.attr.qmui_dialog_content_padding_bottom)
        );

        mTimeTv.setText("10 min");
        mTimeTv.setId(R.id.act_class_setting_dccv_last_time_tv_last_time);



        mSeekBar = new BubbleSeekBar(mContext);
        mSeekBar.getConfigBuilder()
                .min(0).max(150)
                .progress(mSeekProgress)
                .showSectionText()
                .showSectionMark()
                .showThumbText()
                .sectionCount(5)
                .build();
        mSeekBar.setId(R.id.act_class_setting_dccv_last_time_bsb_seek_time);

        mSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                mTimeTv.setText(formatProgress(progress));
            }
        });

    }

    @Override
    protected void onCreateContent(QMUIDialog dialog, ViewGroup parent) {
        mMainLayout = new RelativeLayout(mContext);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = QMUIResHelper.getAttrDimen(mContext, hasTitle() ? com.qmuiteam.qmui.R.attr.qmui_dialog_edit_content_padding_top : com.qmuiteam.qmui.R.attr.qmui_dialog_content_padding_top_when_no_title);
        lp.leftMargin = QMUIResHelper.getAttrDimen(mContext, com.qmuiteam.qmui.R.attr.qmui_dialog_padding_horizontal);
        lp.rightMargin = QMUIResHelper.getAttrDimen(mContext, com.qmuiteam.qmui.R.attr.qmui_dialog_padding_horizontal);
        lp.bottomMargin = QMUIResHelper.getAttrDimen(mContext, com.qmuiteam.qmui.R.attr.qmui_dialog_edit_content_padding_bottom);

        mMainLayout.setBackgroundResource(com.qmuiteam.qmui.R.drawable.qmui_edittext_bg_border_bottom);

        mMainLayout.setLayoutParams(lp);

        mTimeTv.setText(formatProgress(mSeekProgress));
        mSeekBar.setProgress(mSeekProgress);

        parent.addView(mTimeTv, createTimeTvLayoutParams());
        parent.addView(mSeekBar, createSeekBarLayoutParams());
    }

    @NonNull
    private String formatProgress(int progress) {
        return progress + "    min";
    }

    RelativeLayout.LayoutParams createSeekBarLayoutParams(){
        RelativeLayout.LayoutParams seekLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        seekLp.setMargins(
                QMUIResHelper.getAttrDimen(mContext, com.qmuiteam.qmui.R.attr.qmui_dialog_padding_horizontal),
                0,
                QMUIResHelper.getAttrDimen(mContext, com.qmuiteam.qmui.R.attr.qmui_dialog_padding_horizontal),
                QMUIResHelper.getAttrDimen(mContext, com.qmuiteam.qmui.R.attr.qmui_dialog_content_padding_bottom)
        );

        seekLp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        seekLp.addRule(RelativeLayout.BELOW, mTimeTv.getId());

        return seekLp;
    }

    RelativeLayout.LayoutParams createTimeTvLayoutParams(){
        RelativeLayout.LayoutParams timeLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return timeLp;
    }

    public void setSeekProgress(int progress){
        mSeekProgress = progress;
    }

    public TextView getTimeShowTv(){
        return mTimeTv;
    }

    public BubbleSeekBar getSeekBar(){
        return mSeekBar;
    }
}
