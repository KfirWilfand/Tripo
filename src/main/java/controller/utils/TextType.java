package controller.utils;

public enum TextType {
    PersonalExperience {
        @Override
        public String toString() {
            return "PersonalExperience";
        }
    }, Promotion {
        @Override
        public String toString() {
            return "Promotion";
        }
    }
}
