package com.cserspring;

public class SimpleTokenBucket {
	private final long capacity;
	private final long intervalInMills;
	private final double rate;
	private long last;
	private long tokens;
	public SimpleTokenBucket(long n, long intervalInMills) {
		this.capacity = n;
		this.intervalInMills = intervalInMills;
		this.rate = intervalInMills * 1.0 / n;
		this.last = System.currentTimeMillis();
		this.tokens = n;
	}
	
	public boolean take(){
		long now = System.currentTimeMillis();
		long durationSinceLast = now - last;
		if (durationSinceLast >= intervalInMills)
			tokens = capacity;
		else
			tokens = Math.min(capacity, tokens + (long) (durationSinceLast / rate));
		last = now;
		if (tokens < 1) return false;
		--tokens;
		return true;
	}
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		SimpleTokenBucket s = new SimpleTokenBucket(10, 1 * 1000);
		Thread.sleep(1000L);
		for (int i = 0; i < 12; ++i)
			System.out.println(s.take());
		Thread.sleep(2000L);
		for (int i = 0; i < 12; ++i)
			System.out.println(s.take());
	}

}
