class WrappedText {
  String wrapWord = "";
  char[] wrapWordChars;
  float rotateText = 0;
  WrappedText(String word) {
    wrapWord = word + "  ";
    wrapWordChars = new char[wrapWord.length()];
    wrapWordChars = getWrapWordChars();
  }
  
  char[] getWrapWordChars() {
    char[] chars = new char[wrapWord.length()];
    for(int i = (wrapWord.length() - 1); i >= 0; i--) {
      chars[i] = wrapWord.charAt(i);
    }
    return chars;
  }
  
  void animateLetters(float speed) {
     fill(255);
     int length = wrapWordChars.length;
     textSize((225 - ((length - 5) * 15)));
     float increment = ((2*PI) / (length));
     for(int i = 0; i < length; i++) {
       pushMatrix();
         translate(width/2, height/2);
         rotate(-1 * i * increment + rotateText);
         scale(1, -1);
         translate(0, 250);
         textAlign(CENTER);
         pushMatrix();
           //rotate((i * increment));
           //scale(1, -1);
           text(wrapWordChars[i], 0, 0);
         popMatrix();
       popMatrix();
       rotateText = rotateText - (speed/1000);
     }
  }
}
