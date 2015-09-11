
package com.phoenixmarketcity.android.phoenix.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

import com.phoenixmarketcity.android.phoenix.R;
import com.phoenixmarketcity.android.phoenix.util.TypefaceCache;

public class PhoenixTextView extends TextView {
    private int typefaceCode;
    public PhoenixTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            // Get Custom Attribute Name and value
            TypedArray styledAttrs = context.obtainStyledAttributes(attrs,
                    R.styleable.PhoenixFontView);
            typefaceCode = styledAttrs.getInt(R.styleable.PhoenixFontView_fontType, -1);
            Typeface typeface = TypefaceCache.get(context.getAssets(), typefaceCode);
            setTypeface(typeface);

            styledAttrs.recycle();
            if (isInEditMode()) {
                return;
            }
        }
    }

    public PhoenixTextView(Context context) {
        super(context);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text != null) {
            text = Html.fromHtml(text.toString());
        }
        super.setText(text, type);
    }

}// End of PhoenixTextView Class
