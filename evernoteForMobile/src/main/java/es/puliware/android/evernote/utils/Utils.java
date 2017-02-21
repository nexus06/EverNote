package es.puliware.android.evernote.utils;

import android.text.Html;
import android.text.Spanned;
import com.evernote.client.android.EvernoteUtil;

/**
 * Created by nexus07 on 21/02/17.
 *
 * @class Utils
 *
 * @brief Helper methods shared by various Activities.
 */
public class Utils {

    public static String getProcessedContent(String rawContent){
        StringBuilder processedContent = new StringBuilder();
        processedContent.append(EvernoteUtil.NOTE_PREFIX);
        processedContent.append(rawContent);
        processedContent.append(EvernoteUtil.NOTE_SUFFIX);
        return processedContent.toString();
    }

    public static Spanned getFormatedContent(String mItem) {
        return Html.fromHtml(mItem);
    }
}
