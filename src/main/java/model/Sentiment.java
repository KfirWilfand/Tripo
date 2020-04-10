package model;

public class Sentiment {
    int veryNegativeCountWords;
    int negativeCountWords;
    int naturalCountWords;
    int positiveCountWords;
    int veryPositiveCountWords;
    int veryNegativeCountSentences;
    int negativeCountSentences;
    int naturalCountSentences;
    int positiveCountSentences;
    int veryPositiveCountSentences;

    public Sentiment() {
        veryNegativeCountWords = 0;
        negativeCountWords = 0;
        naturalCountWords = 0;
        positiveCountWords = 0;
        veryPositiveCountWords = 0;
        veryNegativeCountSentences = 0;
        negativeCountSentences = 0;
        naturalCountSentences = 0;
        positiveCountSentences = 0;
        veryPositiveCountSentences = 0;
    }

    public void incVeryNegativeCountWords() {
        this.veryNegativeCountWords++;
    }

    public void incNegativeCountWords() {
        this.negativeCountWords++;
    }

    public void incNaturalCountWords() {
        this.naturalCountWords++;
    }

    public void incPositiveCountWords() {
        this.positiveCountWords++;
    }

    public void incVeryPositiveCountWords() {
        this.veryPositiveCountWords++;
    }

    public int getVeryNegativeCountWords() {
        return veryNegativeCountWords;
    }

    public int getNegativeCountWords() {
        return negativeCountWords;
    }

    public int getNaturalCountWords() {
        return naturalCountWords;
    }

    public int getPositiveCountWords() {
        return positiveCountWords;
    }

    public int getVeryPositiveCountWords() {
        return veryPositiveCountWords;
    }

    public void incVeryNegativeCountSentences() {
        this.veryNegativeCountSentences++;
    }

    public void incNegativeCountSentences() {
        this.negativeCountSentences++;
    }

    public void incNaturalCountSentences() {
        this.naturalCountSentences++;
    }

    public void incPositiveCountSentences() {
        this.positiveCountSentences++;
    }

    public void incVeryPositiveCountSentences() {
        this.veryPositiveCountSentences++;
    }

    public int getVeryNegativeCountSentences() {
        return veryNegativeCountSentences;
    }

    public int getNegativeCountSentences() {
        return negativeCountSentences;
    }

    public int getNaturalCountSentences() {
        return naturalCountSentences;
    }

    public int getPositiveCountSentences() {
        return positiveCountSentences;
    }

    public int getVeryPositiveCountSentences() {
        return veryPositiveCountSentences;
    }

    public void setVeryNegativeCountWords(int veryNegativeCountWords) {
        this.veryNegativeCountWords = veryNegativeCountWords;
    }

    public void setNegativeCountWords(int negativeCountWords) {
        this.negativeCountWords = negativeCountWords;
    }

    public void setNaturalCountWords(int naturalCountWords) {
        this.naturalCountWords = naturalCountWords;
    }

    public void setPositiveCountWords(int positiveCountWords) {
        this.positiveCountWords = positiveCountWords;
    }

    public void setVeryPositiveCountWords(int veryPositiveCountWords) {
        this.veryPositiveCountWords = veryPositiveCountWords;
    }

    public void setVeryNegativeCountSentences(int veryNegativeCountSentences) {
        this.veryNegativeCountSentences = veryNegativeCountSentences;
    }

    public void setNegativeCountSentences(int negativeCountSentences) {
        this.negativeCountSentences = negativeCountSentences;
    }

    public void setNaturalCountSentences(int naturalCountSentences) {
        this.naturalCountSentences = naturalCountSentences;
    }

    public void setPositiveCountSentences(int positiveCountSentences) {
        this.positiveCountSentences = positiveCountSentences;
    }

    public void setVeryPositiveCountSentences(int veryPositiveCountSentences) {
        this.veryPositiveCountSentences = veryPositiveCountSentences;
    }
}
