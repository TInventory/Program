package edu.mtu.tinventory.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

	@Override
	public synchronized String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(record.getSourceClassName()).append("]");
		sb.append("[").append(record.getLevel().getName()).append("]: ");
		sb.append(formatMessage(record)).append("\n");
		if(record.getThrown() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			record.getThrown().printStackTrace(pw);
			pw.close();
			sb.append(sw.toString());
		}
		return sb.toString();
	}
}
