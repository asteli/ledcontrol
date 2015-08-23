class RotatingArcsDesign {
 
  int hue_1 = 0;
  float theta_1 = 0;
  int hue_2 = 0;
  float theta_2 = 0;
  int hue_3 = 0;
  float theta_3 = 0;
  int hue_4 = 0;
  float theta_4 = 0;
 RotatingArcsDesign() {
 }
 
 void run() {
    background(5, 5, 5);
//    
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
  
    fill(100, 255, 250, 140);
    arc(width/2, height/2, width, height, theta_1, (theta_1 + 0.4) );
  
//    if (hue_2 < 252) {
//      hue_2 += 1;
//    } else {
//      hue_2 = 0;
//    }
//    
//    if (theta_2 < TWO_PI) {
//      theta_2 += 0.1;
//    } else {
//      theta_2 = 0;
//    }
//    
//    fill(hue_3, 255, 250, 140);
//    arc(width/2, height/2, width, height, theta_2, (theta_2 + 0.5) );
//  
//    if (hue_3 < 255) {
//      hue_3 += 5;
//    } else {
//      hue_3 = 0;
//    }
//    
//    if (theta_3 < TWO_PI) {
//      theta_3 += 0.02;
//    } else {
//      theta_3 = 0;
//    }
//  
//    fill(hue_3, 255, 250, 80);
//    arc(width/2, height/2, width, height, theta_3, (theta_3 + 1));
//    
//    if (hue_4 < 250) {
//      hue_4 += 3.5;
//    } else {
//      hue_4 = 0;
//    }
//    
//    if (theta_4 < TWO_PI) {
//      theta_3 += 0.07;
//    } else {
//      theta_3 = 0;
//    }
//  
//    fill(hue_4, 255, 100, 80);
//    arc(width/2, height/2, width, height, theta_4, (theta_4 + 1));
 }
}
  
//    noFill();
//    
//    stroke(255, 255, 128);
//    strokeWeight(36);
//  
//    ellipse(width/2, height/2, rad1*2, rad1*2);
//    
//    if (rad1 > 175) {
//      rad1 = 0;
//    } else {
//      rad1 = rad1 + 4;
//    }
//  
//    stroke(24, 255, 255, 128);
//    strokeWeight(36);
//    ellipse(width/2, height/2, rad2*2, rad2*2);
//    
//    if (rad2 > 175) {
//      rad2 = 0;
//    } else {
//      rad2 = rad2 + 4;
//    }  
//    
//    stroke(128, 255, 255, 128);
//    strokeWeight(36);
//    ellipse(width/2, height/2, rad3*2, rad3*2);
//    
//    if (rad3 > 175) {
//      rad3 = 0;
//    } else {
//      rad3 = rad3 + 4;
//    }
//  
//    stroke(64, 255, 255, 128);
//    strokeWeight(36);
//    ellipse(width/2, height/2, rad4*2, rad4*2);
//    
//    if (rad4 > 175) {
//      rad4 = 0;
//    } else {
//      rad4 = rad4 + 4;
//    }
//  
//    stroke(200, 255, 255, 128);
//    strokeWeight(36);
//    ellipse(width/2, height/2, rad5*2, rad5*2);
//    
//    if (rad5 > 175) {
//      rad5 = 0;
//    } else {
//      rad5 = rad5 + 4;
//    }
//  }
//}
