package in.ashish29agre.passcode;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashish on 25/12/15.
 */
public class PasscodeView extends LinearLayout implements
        KeyCallback {

    private String TAG = PasscodeView.class.getSimpleName();

    private Drawable activeDrawable;
    private Drawable inActiveDrawable;
    private int textColor;
    private int drawableSize;
    private int passcodeItemMargin;

    private int passCodeCount;
    private List<View> passCodeViewsList;
    private int currentViewPos = 0;
    private StringBuilder passcodeItems;

    private PasscodeCallback passcodeCallback;

    public PasscodeView(Context context) {
        super(context);
        initDefaultConfig();
    }

    public PasscodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaultConfig();
    }

    public PasscodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultConfig();
    }

    public PasscodeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initDefaultConfig();
    }

    private void initDefaultConfig() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        passCodeViewsList = new ArrayList<>();
        activeDrawable = ContextCompat.getDrawable(getContext(), R.drawable.bg_active);
        inActiveDrawable = ContextCompat.getDrawable(getContext(), R.drawable.bg_in_active);
        textColor = android.R.color.transparent;
        drawableSize = 72;
        passcodeItemMargin = 8;
        passcodeItems = new StringBuilder();
    }


    public void setPassCodeCount(int passCodeCount) {
        this.passCodeCount = passCodeCount;
        invalidate();
    }

    public int getPassCodeCount() {
        return passCodeCount;
    }


    private void addPasscodeViews() {
        removeAllViews();
        removeAllViewsInLayout();
        if (passCodeCount < 0) {
            throw new IllegalStateException("Invalid PassCodeCount > 0 required");
        }
        for (int i = 0; i < this.passCodeCount; i++) {
            PasscodeEditText view = new PasscodeEditText(getContext());
            view.setGravity(Gravity.CENTER);
            view.setKeyCallback(this);
            view.setCursorVisible(false);
            view.setRawInputType(InputType.TYPE_CLASS_NUMBER);
            view.setInputType(InputType.TYPE_CLASS_NUMBER);
            view.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            view.setTextColor(ContextCompat.getColor(getContext(), textColor));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(inActiveDrawable);
            } else {
                view.setBackgroundDrawable(inActiveDrawable);
            }
            view.setLayoutParams(getPassCodeLayoutParams());
            addView(view, i);
            passCodeViewsList.add(view);
        }

    }

    private LayoutParams getPassCodeLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(drawableSize, drawableSize);
        layoutParams.setMargins(passcodeItemMargin, passcodeItemMargin, passcodeItemMargin, passcodeItemMargin);
        return layoutParams;
    }


    public void setActiveDrawable(Drawable defaultActiveDrawable) {
        this.activeDrawable = defaultActiveDrawable;
        invalidate();
    }

    public Drawable getActiveDrawable() {
        return activeDrawable;
    }

    public void seInActiveDrawable(Drawable defaultInActiveDrawable) {
        this.inActiveDrawable = defaultInActiveDrawable;
        invalidate();
    }

    public Drawable getInActiveDrawable() {
        return inActiveDrawable;
    }


    public void setPasscodeItemMargin(int passcodeItemMargin) {
        this.passcodeItemMargin = passcodeItemMargin;
        invalidate();
    }


    public void setDrawableSize(int drawableSize) {
        this.drawableSize = drawableSize;
    }

    public int getDrawableSize() {
        return drawableSize;
    }

    public void setPasscodeCallback(PasscodeCallback passcodeCallback) {
        this.passcodeCallback = passcodeCallback;
    }

    public PasscodeCallback getPasscodeCallback() {
        return passcodeCallback;
    }

    @Override
    public void onPrevious() {
        if (currentViewPos == 0) {
            return;
        }
        passcodeItems.deleteCharAt(passcodeItems.length() - 1);
        --currentViewPos;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            passCodeViewsList.get(currentViewPos).setBackground(inActiveDrawable);
        } else {
            passCodeViewsList.get(currentViewPos).setBackgroundDrawable(inActiveDrawable);
        }

        if (currentViewPos >= 0) {
            passCodeViewsList.get(currentViewPos).requestFocus();
        }
    }

    @Override
    public void onNext(char value) {
        if (currentViewPos == this.passCodeCount) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            passCodeViewsList.get(currentViewPos).setBackground(activeDrawable);
        } else {
            passCodeViewsList.get(currentViewPos).setBackgroundDrawable(inActiveDrawable);
        }
        if (currentViewPos < this.passCodeCount) {
            passCodeViewsList.get(currentViewPos).requestFocus();
            passcodeItems.append(value);
            ++currentViewPos;
        }
        if (passcodeItems.length() == this.passCodeCount) {
            if (passcodeCallback != null) {
                passcodeCallback.onComplete(passcodeItems.toString());
            }
        }
    }

    @Override
    public void onDone() {
    }

    @Override
    public void onDelete() {
        onPrevious();
    }


    @Override
    protected void onDetachedFromWindow() {
        passCodeViewsList = null;
        activeDrawable = null;
        inActiveDrawable = null;
        super.onDetachedFromWindow();

    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindowToken(), 0);
    }
    public void show() {
        addPasscodeViews();
    }
}
