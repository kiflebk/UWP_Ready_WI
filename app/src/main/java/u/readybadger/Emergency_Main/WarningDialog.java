package u.readybadger.Emergency_Main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class WarningDialog extends DialogFragment {

    public WarningDialog() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Reminder")
                .setMessage("This is not an official FEMA report. \n\nDo not forget to call in your" +
                " report.")
                .setPositiveButton("OK",null);

        return builder.create();
    }


}
