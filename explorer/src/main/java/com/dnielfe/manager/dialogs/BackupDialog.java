package com.dnielfe.manager.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.dnielfe.manager.R;
import com.dnielfe.manager.adapters.BrowserTabsAdapter;
import com.dnielfe.manager.tasks.PasteTask;
import com.dnielfe.manager.utils.SimpleUtils;

import java.io.File;
import java.lang.ref.WeakReference;

public final class BackupDialog extends DialogFragment {

    private static String files;

    public static DialogFragment instantiate(String files1) {
        files = files1;
        return new BackupDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle state) {
        final Activity a = getActivity();
        final String backupfile = BrowserTabsAdapter.getCurrentBrowserFragment().mCurrentPath
                + "/";

        // Set an EditText view to get user input
        final EditText inputf = new EditText(a);
        inputf.setHint(R.string.enter_name);
        inputf.setText(files);

        final AlertDialog.Builder b = new AlertDialog.Builder(a);
        b.setTitle(getString(R.string.backup) + "...");
        b.setView(inputf);
        b.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String newpath = backupfile + inputf.getText().toString();
                        File file = new File(newpath);

                        if (file.exists()) {
                            Toast.makeText(a, a.getString(R.string.fileexists),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        dialog.dismiss();
                        SimpleUtils.copyFile(new File(backupfile + files), file, a);
                    }
                });
        b.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return b.create();
    }
}
