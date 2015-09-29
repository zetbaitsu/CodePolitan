/*
 * Copyright (c) 2015 Zelory.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package id.zelory.codepolitan.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.GridHolder;
import com.orhanobut.dialogplus.ViewHolder;

import butterknife.Bind;
import butterknife.OnClick;
import id.zelory.benih.fragment.BenihFragment;
import id.zelory.codepolitan.R;
import id.zelory.codepolitan.data.LocalDataManager;
import id.zelory.codepolitan.ui.HelpActivity;
import id.zelory.codepolitan.ui.SingleFragmentActivity;
import id.zelory.codepolitan.ui.adapter.MenuFollowAdapter;

/**
 * Created on : August 4, 2015
 * Author     : zetbaitsu
 * Name       : Zetra
 * Email      : zetra@mail.ugm.ac.id
 * GitHub     : https://github.com/zetbaitsu
 * LinkedIn   : https://id.linkedin.com/in/zetbaitsu
 */
public class SettingFragment extends BenihFragment
{
    @Bind(R.id.cb_notification) CheckBox cbNotification;
    @Bind(R.id.cb_vibrate) CheckBox cbVibrate;

    @Override
    protected int getFragmentView()
    {
        return R.layout.fragment_setting;
    }

    @Override
    protected void onViewReady(Bundle bundle)
    {
        cbVibrate.setChecked(LocalDataManager.isNotificationActive());
        cbNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            LocalDataManager.setNotificationActive(isChecked);
            cbVibrate.setEnabled(isChecked);
        });
        cbVibrate.setOnCheckedChangeListener((buttonView, isChecked) -> LocalDataManager.setVibrate(isChecked));
        cbVibrate.setEnabled(LocalDataManager.isNotificationActive());
    }

    @OnClick(R.id.ll_ringtone)
    public void chooseRingtone()
    {
        if (LocalDataManager.isNotificationActive())
        {
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Ringtone");
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, LocalDataManager.getRingtone() == null ? null :
                    Uri.parse(LocalDataManager.getRingtone()));
            startActivityForResult(intent, 5);
        }
    }

    @OnClick(R.id.rl_about)
    public void onAboutClick()
    {
        Intent intent = new Intent(getActivity(), SingleFragmentActivity.class);
        intent.putExtra("type", SingleFragmentActivity.TYPE_ABOUT);
        startActivity(intent);
    }

    @OnClick(R.id.rl_about_dev)
    public void onAboutDeveloperClick()
    {
        Intent intent = new Intent(getActivity(), SingleFragmentActivity.class);
        intent.putExtra("type", SingleFragmentActivity.TYPE_ABOUT_DEV);
        startActivity(intent);
    }

    @OnClick(R.id.rl_feed_back)
    public void onFeedbackClick()
    {
        DialogPlus dialogPlus = DialogPlus.newDialog(getActivity())
                .setContentHolder(new ViewHolder(R.layout.dialog_feedback))
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .create();

        ImageView send = (ImageView) dialogPlus.getHolderView().findViewById(R.id.iv_send);
        EditText feedback = (EditText) dialogPlus.getHolderView().findViewById(R.id.et_feedback);
        send.setOnClickListener(v -> onSendFeedBack(feedback));
        dialogPlus.show();
    }

    private void onSendFeedBack(EditText feedback)
    {
        if (feedback.getText() == null || "".equals(feedback.getText().toString()))
        {
            feedback.setError("Please write your feedback here!");
        } else
        {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:zetra@mail.ugm.ac.id"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "CodePolitan Feedback");
            intent.putExtra(Intent.EXTRA_TEXT, feedback.getText() + "\n\nSent from CodePolitan Apps.");
            startActivity(intent);
        }
    }

    @OnClick(R.id.rl_follow)
    public void onFollowClick()
    {
        DialogPlus.newDialog(getActivity())
                .setContentHolder(new GridHolder(3))
                .setHeader(R.layout.menu_header_follow)
                .setFooter(R.layout.menu_footer_share)
                .setCancelable(true)
                .setAdapter(new MenuFollowAdapter(getActivity()))
                .setOnItemClickListener((dialogPlus, o, view, i) -> {
                    switch (i)
                    {
                        case 0:
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                                     Uri.parse("http://www.google.com/+codepolitan")));
                            break;
                        case 1:
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                                     Uri.parse("http://www.facebook.com/codepolitan")));
                            break;
                        case 2:
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                                     Uri.parse("http://twitter.com/codepolitan")));
                            break;
                    }
                    dialogPlus.dismiss();
                })
                .create()
                .show();
    }

    @OnClick(R.id.rl_help)
    public void onHelpClick()
    {
        startActivity(new Intent(getActivity(), HelpActivity.class));
    }

    @OnClick(R.id.rl_rate)
    public void onRateClick()
    {
        startActivity(new Intent(Intent.ACTION_VIEW,
                                 Uri.parse("http://play.google.com/store/apps/details?id=id.zelory.codepolitan")));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK && requestCode == 5)
        {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (uri != null)
            {
                LocalDataManager.setRingtone(uri.toString());
            }
        }
    }
}
