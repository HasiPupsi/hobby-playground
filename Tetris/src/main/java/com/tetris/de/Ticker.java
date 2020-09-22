package com.tetris.de;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Ticker {

	private Optional<Timeline> timeline = Optional.empty();
	private List<TickerListener> listeners = new ArrayList<>();

	public void addTickerListener(TickerListener listener) {
		this.listeners.add(listener);
	}

	public void removeTickerListener(TickerListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * 
	 * @param period in ms
	 */
	public void startTimeLine(long period) {
		Timeline newTimeline = null;
		if (!timeline.isPresent()) {
			newTimeline = new Timeline();
		} else {
			newTimeline = this.timeline.get();
			newTimeline.stop();
			newTimeline.getKeyFrames().clear();
		}
		newTimeline.setDelay(Duration.millis(period));
		newTimeline.setCycleCount(Animation.INDEFINITE);
		newTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(period), ev -> {
			this.listeners.forEach(TickerListener::tick);
		}));
		newTimeline.play();
		timeline = Optional.of(newTimeline);
	}

}
