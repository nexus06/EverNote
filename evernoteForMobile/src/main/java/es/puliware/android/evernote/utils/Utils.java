package es.puliware.android.evernote.utils;

import com.evernote.client.android.EvernoteUtil;

/**
 * Created by nexus07 on 21/02/17.
 */
public class Utils {

    public static String getProcessedContent(String rawContent){
        StringBuilder processedContent = new StringBuilder();
        processedContent.append(EvernoteUtil.NOTE_PREFIX);
        processedContent.append(rawContent);
        processedContent.append(EvernoteUtil.NOTE_SUFFIX);
        return processedContent.toString();
    }

}
