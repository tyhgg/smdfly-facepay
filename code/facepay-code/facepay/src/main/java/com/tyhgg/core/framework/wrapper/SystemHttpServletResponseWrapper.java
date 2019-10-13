package com.tyhgg.core.framework.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class SystemHttpServletResponseWrapper extends HttpServletResponseWrapper {
	/**
	 * 封装ServletOutputStream 内部类
	 */
	private static class WrapperedOutputStream extends ServletOutputStream {
		private ByteArrayOutputStream bos = null;

		public WrapperedOutputStream(ByteArrayOutputStream stream) {
			this.bos = stream;
		}

		@Override
		public void write(int b) throws IOException {
			this.bos.write(b);
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setWriteListener(WriteListener arg0) {
			
		}
	}

	private final ByteArrayOutputStream buffer;

	private final ServletOutputStream out;

	private final PrintWriter writer;

	/**
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public SystemHttpServletResponseWrapper(HttpServletResponse response)
			throws UnsupportedEncodingException {
		super(response);
		this.buffer = new ByteArrayOutputStream();
		this.out = new WrapperedOutputStream(this.buffer);
		this.writer = new PrintWriter(new OutputStreamWriter(this.buffer, this
				.getResponse().getCharacterEncoding()));
	}

	@Override
	public ServletOutputStream getOutputStream() {
		return this.out;
	}

	public byte[] getReponseOutputStream() throws IOException {
		this.flushBuffer();
		return this.buffer.toByteArray();
	}

	public String getResponseData() throws IOException {
		this.flushBuffer();
		return new String(this.buffer.toByteArray(), this.getResponse()
				.getCharacterEncoding());
	}

	@Override
	public void flushBuffer()throws IOException{
		if(out!=null){
			out.flush();
		}
		if(writer!=null){
			writer.flush();
		}
	}
	
	@Override
	public PrintWriter getWriter() {
		return this.writer;
	}

	@Override
	public void reset() {
		this.buffer.reset();
	}

}
