package controller.utils;

public enum TextType {
    PerEx {
        @Override
        public String toString() {
            return "personal_experience";
        }
    }, Promo {
        @Override
        public String toString() {
            return "promotion";
        }
    }
}
