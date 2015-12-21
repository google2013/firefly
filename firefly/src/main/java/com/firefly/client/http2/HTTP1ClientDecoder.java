package com.firefly.client.http2;

import java.nio.ByteBuffer;

import com.firefly.codec.http2.model.HttpVersion;
import com.firefly.codec.http2.stream.HTTPConnection;
import com.firefly.net.DecoderChain;
import com.firefly.net.Session;

public class HTTP1ClientDecoder extends DecoderChain {
	
	public HTTP1ClientDecoder(DecoderChain next) {
		super(next);
	}

	@Override
	public void decode(ByteBuffer buf, Session session) throws Throwable {
		HTTPConnection connection = (HTTPConnection) session.getAttachment();
		if (connection.getHttpVersion() == HttpVersion.HTTP_2) {
			next.decode(buf, session);
		} else if (connection.getHttpVersion() == HttpVersion.HTTP_1_1) {
			// TODO http1 parser
		}
	}

}
