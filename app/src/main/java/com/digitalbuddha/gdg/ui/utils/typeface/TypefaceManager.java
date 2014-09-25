package com.digitalbuddha.gdg.ui.utils.typeface;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

/**
 * The manager of roboto typefaces.
 *
 * @author e.shishkin
 */
public class TypefaceManager
{

    public final static int EDMONDSANS_REGULAR  = 0;
    public final static int EDMONDSANS_MEDIUM   = 1;
    public final static int EDMONDSANS_BOLD     = 2;
    public final static int QUIGLEY = 3;

    public final static int DEFAULT = EDMONDSANS_REGULAR;

    /**
     * Array of created typefaces for later reused.
     */
    private final static SparseArray<Typeface> mTypefaces = new SparseArray<Typeface>(4);

    /**
     * Obtain typeface.
     *
     * @param context       The Context the widget is running in, through which it can access the current theme, resources, etc.
     * @param typefaceValue The value of "typeface" attribute
     * @return specify {@link android.graphics.Typeface}
     * @throws IllegalArgumentException if unknown `typeface` attribute value.
     */
    public static Typeface obtainTypeface(Context context, int typefaceValue) throws IllegalArgumentException {
        Typeface typeface = mTypefaces.get(typefaceValue);
        if (typeface == null) {
            typeface = createTypeface(context, typefaceValue);
            mTypefaces.put(typefaceValue, typeface);
        }
        return typeface;
    }

    /**
     * Create typeface from assets.
     *
     * @param context       The Context the widget is running in, through which it can
     *                      access the current theme, resources, etc.
     * @param typefaceValue The value of "typeface" attribute
     * @return {@link android.graphics.Typeface}
     * @throws IllegalArgumentException if unknown `typeface` attribute value.
     */
    private static Typeface createTypeface(Context context, int typefaceValue) throws IllegalArgumentException {
        Typeface typeface;
        switch (typefaceValue) {
            case EDMONDSANS_REGULAR:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Edmondsans-Regular.otf");
                break;
            case EDMONDSANS_MEDIUM:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Edmondsans-Medium.otf");
                break;
            case EDMONDSANS_BOLD:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Edmondsans-Bold.otf");
                break;
            case QUIGLEY:
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/quigley.ttf");
                break;
            default:
                throw new IllegalArgumentException("Unknown `typeface` attribute value " + typefaceValue);
        }
        return typeface;
    }

}
