package DictionaryApplication;

public class Word {
        private static String wordTarget;
        private static String wordExplain;

        public Word(){
            this.wordTarget = "";
            this.wordExplain = "";
        }

        public Word(String wordTarget, String wordExplain){
            this.wordTarget = wordTarget;
            this.wordExplain = wordExplain;
        }

        public String getWordTarget(){
            return wordTarget;
        }

        public String getWordExplain(){
            return wordExplain;
        }

        @Override
        public String toString(){
            return String.format("%-10s | %-20s", wordTarget, wordExplain);
        }
}