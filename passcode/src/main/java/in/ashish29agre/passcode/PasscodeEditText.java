package in.ashish29agre.passcode;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

/**
 * Created by Ashish on 25/12/15.
 */

public class PasscodeEditText extends EditText {

    private KeyCallback keyCallback;

    public PasscodeEditText(Context context) {
        super(context);
    }

    public PasscodeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasscodeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new PasscodeInputConnection(super.onCreateInputConnection(outAttrs),
                true);
    }


    private class PasscodeInputConnection extends InputConnectionWrapper {

        public PasscodeInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCallback != null) {
                    switch (event.getKeyCode()) {
                        case KeyEvent.KEYCODE_DEL:
                            keyCallback.onDelete();
                            break;
                        case KeyEvent.KEYCODE_0:
                        case KeyEvent.KEYCODE_1:
                        case KeyEvent.KEYCODE_2:
                        case KeyEvent.KEYCODE_3:
                        case KeyEvent.KEYCODE_4:
                        case KeyEvent.KEYCODE_5:
                        case KeyEvent.KEYCODE_6:
                        case KeyEvent.KEYCODE_7:
                        case KeyEvent.KEYCODE_8:
                        case KeyEvent.KEYCODE_9:
                            keyCallback.onNext(event.getNumber());
                            break;
                        case KeyEvent.KEYCODE_NUMPAD_ENTER:
                        case KeyEvent.KEYCODE_ENTER:
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                            keyCallback.onDone();
                            break;
                    }
                } else {
                    throw new IllegalStateException("KeyCallback is null");
                }
            }
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            if (beforeLength == 1 && afterLength == 0) {
                // backspace
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            }
            return super.deleteSurroundingText(beforeLength, afterLength);
        }
    }



    public void setKeyCallback(KeyCallback keyCallback) {
        this.keyCallback = keyCallback;
    }

    public KeyCallback getKeyCallback() {
        return keyCallback;
    }
}