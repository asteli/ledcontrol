class WrappedText {
  PFont font;
  String wrapWord = "";
  char[] wrapWordChars;
  float rotateText = 0;
  WrappedText(String word) {
    wrapWord = word + "  ";
    wrapWordChars = new char[wrapWord.length()];
    wrapWordChars = getWrapWordChars();
    font = loadFont("Arial-Black-150.vlw");
  }
  
  char[] getWrapWordChars() {
    char[] chars = new char[wrapWord.length()];
    for(int i = 0 ; i < wrapWord.length(); i++) {
      chars[i] = wrapWord.charAt(i);
    }
    return chars;
  }
  
  void animateLetters(float speed) {
     fill(255);
     char[] chars = new char[wrapWord.length()];
     chars = reverse(wrapWordChars);
     int length = wrapWordChars.length;
     textFont(font, (150 - ((length - 5) * 15)));
     float increment = ((2*PI) / (length));
     for(int i = 0; i < length; i++) {
       pushMatrix();
         translate(width/2, height/2);
         rotate(-1 * i * increment + rotateText);
         scale(1, -1);
         translate(0, width/2.5);
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
  
  void animateLettersLine(float speed) {
    // center is wrapWord.textWidth()
    // range would start great, then subtract an interval
    int length = wrapWordChars.length;
    textFont(font, (150 - ((length - 5) * 15)));
    textAlign(CENTER);
    float interval = textWidth(wrapWord) / wrapWord.length() * 1.5;
    for(int i = (length - 1); i >= 0; i--) {
      text(wrapWordChars[i], (width/2 - (textWidth(wrapWord)/2.7) + (interval * i)),
      height/2 + width/2.5);
    }
  }
}
