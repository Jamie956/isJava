package com.example;

import java.util.*;

public class Sentence extends LetterComposite {
	public Sentence(List<Word> words) {
		for (Word w : words) {
			this.add(w);
		}
	}

	@Override
	protected void printThisAfter() {
		System.out.print(".");
	}
}
