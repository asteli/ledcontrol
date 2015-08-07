class ThreeArcDesign {
 
  int hue_1 = 0;
  float theta_1 = 0;
  int hue_2 = 0;
  float theta_2 = 0;
  int hue_3 = 0;
  float theta_3 = 0;
  
 ThreeArcDesign() {
 }
 
 void run() {
    if (hue_1 > 0) {
      hue_1 -= 3;
    } else {
      hue_1 = 255;
    }
    
    if (theta_1 > 0.05) {
      theta_1 -= 0.05;
    } else {
      theta_1 = TWO_PI;
    }
    fill(hue_1, 255, 250, 140);
    arc(width/2, height/2, width, height, theta_1, (theta_1 + 0.5) );
    if (hue_2 < 252) {
      hue_2 += 1;
    } else {
      hue_2 = 0;
    }
    
    if (theta_2 < TWO_PI) {
      theta_2 += 0.1;
    } else {
      theta_2 = 0;
    }
    fill(hue_3, 255, 250, 140);
    arc(width/2, height/2, width, height, theta_2, (theta_2 + 0.5) );
  
    if (hue_3 < 255) {
      hue_3 += 5;
    } else {
      hue_3 = 0;
    }
    
    if (theta_3 < TWO_PI) {
      theta_3 += 0.02;
    } else {
      theta_3 = 0;
    }  
    fill(hue_3, 255, 250, 80);
    arc(width/2, height/2, width, height, theta_3, (theta_3 + 1) );
    ellipse(mouseX, mouseY, 100, 100); 
   }
}
