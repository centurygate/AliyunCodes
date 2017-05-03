package exceptionprocess;

import log.SystemLog;

/**
 * Created by free on 12/11/16.
 */
public class Exception2Msg {
    static public String convertException2Msg(Exception e) {
        StringBuilder stringBuilder = new StringBuilder();
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        stringBuilder.append(e.getLocalizedMessage()).append("\r\n");
        for (int i = 0; i < stackTraceElements.length; i++) {
            stringBuilder.append(stackTraceElements[i].toString()).append("\r\n");
        }
        return stringBuilder.toString();
    }
}
