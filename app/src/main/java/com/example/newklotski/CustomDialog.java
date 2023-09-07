package com.example.newklotski;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.newklotski.R;

/**
 * CustomDialog is a custom implementation of the Dialog class.
 * It provides a convenient way to create a custom dialog with various options for customization.
 */
public class CustomDialog extends Dialog {

    /**
     * Constructs a new CustomDialog instance.
     *
     * @param context The context in which the dialog should appear.
     */
    public CustomDialog(Context context) {
        super(context);
    }

    /**
     * Constructs a new CustomDialog instance with a specified theme.
     *
     * @param context The context in which the dialog should appear.
     * @param theme   The resource ID of the dialog's theme.
     */
    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * Builder is a helper class for creating instances of CustomDialog.
     * It provides methods for setting various properties of the dialog.
     */
    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;

        /**
         * Constructs a new Builder instance.
         *
         * @param context The context in which the dialog should appear.
         */
        public Builder(Context context) {
            this.context = context;
        }

        /**
         * Sets the message to be displayed in the dialog.
         *
         * @param message The message to be displayed.
         * @return The Builder instance.
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Sets the message to be displayed in the dialog from a string resource.
         *
         * @param message The string resource ID of the message to be displayed.
         * @return The Builder instance.
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Sets the title of the dialog from a string resource.
         *
         * @param title The string resource ID of the title.
         * @return The Builder instance.
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Sets the title of the dialog.
         *
         * @param title The title to be displayed.
         * @return The Builder instance.
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the custom view to be displayed in the dialog.
         *
         * @param v The custom view.
         * @return The Builder instance.
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Sets the positive button with a specified text and click listener.
         *
         * @param positiveButtonText The text to be displayed on the positive button.
         * @param listener           The click listener for the positive button.
         * @return The Builder instance.
         */
        public Builder setPositiveButton(String positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Sets the positive button with a specified text and click listener.
         *
         * @param positiveButtonText The string resource ID of the text to be displayed on the positive button.
         * @param listener           The click listener for the positive button.
         * @return The Builder instance.
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Sets the negative button with a specified text and click listener.
         *
         * @param negativeButtonText The text to be displayed on the negative button.
         * @param listener           The click listener for the negative button.
         * @return The Builder instance.
         */
        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Sets the negative button with a specified text and click listener.
         *
         * @param negativeButtonText The string resource ID of the text to be displayed on the negative button.
         * @param listener           The click listener for the negative button.
         * @return The Builder instance.
         */
        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener){
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Creates a new instance of CustomDialog with the specified properties.
         *
         * @return The created CustomDialog instance.
         */
        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Instantiate the dialog with the custom Theme
            final CustomDialog dialog = new CustomDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_normal_layout, null);
            dialog.addContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            // Set the dialog title
            ((TextView) layout.findViewById(R.id.title)).setText(title);
            // Set the positive button
            if (positiveButtonText != null) {
                ((TextView) layout.findViewById(R.id.positiveTextView))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.positiveTextView))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // If no positive button, set the visibility to GONE
                layout.findViewById(R.id.positiveTextView).setVisibility(
                        View.GONE);
            }
            // Set the negative button
            if (negativeButtonText != null) {
                ((TextView) layout.findViewById(R.id.negativeTextView))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.negativeTextView))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // If no negative button, set the visibility to GONE
                layout.findViewById(R.id.negativeTextView).setVisibility(
                        View.GONE);
            }
            // Set the content message
            if (message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(message);
            } else if (contentView != null) {
                // If no message, add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(contentView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
