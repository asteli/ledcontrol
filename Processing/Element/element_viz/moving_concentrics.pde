class MovingConcentrics {
  float ellipseWidth;
  int numCircles = 3;
  int sphereWidth;
  MovingConcentrics(int ew) {
    sphereWidth = ew;
    ellipseWidth = sphereWidth;
  }
  
  void run() {
    for(int i = 0; i < numCircles; i++) {
      generateCirclePairWithAlphaAndOffset(255 - (i*110), (i*30));
    }
    if(ellipseWidth > 0) {
      ellipseWidth -= 15;
    } else {
      ellipseWidth = sphereWidth;
    }
  }
  
  void manualCircleSet() {
    
  }
  
  void generateCirclePairWithAlphaAndOffset(int alpha, int offSet) {
    noFill();
    stroke(alpha, 255);
    strokeWeight(15);
    if(offSet == 0) {
      ellipse(width/2, height/2, ellipseWidth + offSet, ellipseWidth);
    } else {
      ellipse(width/2, height/2, ellipseWidth + offSet, ellipseWidth + offSet);
      ellipse(width/2, height/2, ellipseWidth - offSet, ellipseWidth - offSet);
    }
  }
}
