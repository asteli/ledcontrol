OPC opc;
int bpm;
int selectedSequence;
TapInput tapInput;
ThreeArcDesign threeArcDesign;
MovingConcentrics movingConcentrics;
WrappedText wrappedText;
RotatingArcsDesign rotatingArcs;
Strobe strobe;

void setup()
{
  noStroke();
  size(400, 400);
  colorMode(HSB);
  background(50);
  selectedSequence = 4;

  // Connect to the local instance of fcserver
  opc = new OPC(this, "127.0.0.1", 7890);
  opc.showLocations(true);

  create_opc_leds();
<<<<<<< Updated upstream
  
  threeArcDesign = new ThreeArcDesign();
  movingConcentrics = new MovingConcentrics(350);
  rotatingArcs = new RotatingArcsDesign();
  wrappedText = new WrappedText("SING");
  strobe = new Strobe();
  tapInput = new TapInput();

}

void draw() {
  background(0);
  
  switch(selectedSequence) {
    case 0:
      threeArcDesign.run();
      break;
    case 1:
      movingConcentrics.run();
      break;
    case 2:
      wrappedText.animateLetters(1);
      break;
    case 3:
      strobe.setStartTime();
      strobe.pulseLoop();
      break;
    case 4:
      rotatingArcs.run();
      break;
    default:
      threeArcDesign.run();
      break;
=======

}

int hue_1 = 0;
float theta_1 = 0;
int hue_2 = 0;
float theta_2 = 0;
int hue_3 = 0;
float theta_3 = 0;


int rad1 = 12;
int rad2 = 75;
int rad3 = 150;
int rad4 = 225;
int rad5 = 300;

void draw()
{
  background(180, 180, 100);
  
  
  if (hue_1 > 0)
  {
    hue_1 -= 3;
  }
  else
  {
    hue_1 = 255;
  }
  
  if (theta_1 > 0.05)
  {
    theta_1 -= 0.05;
  }
  else
  {
    theta_1 = TWO_PI;
  }

  
  fill(100, 255, 250, 255);
  
  arc(width/2, height/2, width, height, theta_1, (theta_1 + 0.4) );
  

  if (hue_2 < 252)
  {
    hue_2 += 1;
  }
  else
  {
    hue_2 = 0;
  }
  
  if (theta_2 < TWO_PI)
  {
    theta_2 += 0.1;
  }
  else
  {
    theta_2 = 0;
  }
  
  
  fill(hue_3, 255, 250, 255);
  
  arc(width/2, height/2, width, height, theta_2, (theta_2 + 0.5) );

  if (hue_3 < 255)
  {
    hue_3 += 5;
  }
  else
  {
    hue_3 = 0;
  }
  
  if (theta_3 < TWO_PI)
  {
    theta_3 += 0.1;
  }
  else
  {
    theta_3 = 0;
  }
  
  
//  fill(hue_3, 255, 250, 255);
  
  fill(hue_3, 0, 255, 255); 

  arc(width/2, height/2, width, height, theta_3, (theta_3 + 0.3 ));




  // ellipse(mouseX, mouseY, 100, 100);


  noFill();
  
  stroke(255, 255, 128);
  strokeWeight(80);

  ellipse(width/2, height/2, rad1*2, rad1*2);
  
  if (rad1 > 60)
  {
    rad1 = 0;
  }
  else
  {
    rad1+=1;
  }


  stroke(24, 255, 255, 128);
  strokeWeight(80);

  ellipse(width/2, height/2, rad2*2, rad2*2);
  
  if (rad2 > 200)
  {
    rad2 = 0;
  }
  else
  {
    rad2+=1;
  }   
  
  
  stroke(128, 255, 255, 128);
  strokeWeight(80);

  ellipse(width/2, height/2, rad3*2, rad3*2);
  
  if (rad3 > 200)
  {
    rad3 = 0;
  }
  else
  {
    rad3+=1;
  }

  stroke(64, 255, 255, 128);
  strokeWeight(80);

  ellipse(width/2, height/2, rad4*2, rad4*2);
  
  if (rad4 > 200)
  {
    rad4 = 0;
  }
  else
  {
    rad4+=1;
>>>>>>> Stashed changes
  }
}

<<<<<<< Updated upstream
void mouseClicked() {
  if(selectedSequence == 3) {
    tapInput.strobeTap(strobe);
  }
=======

  stroke(200, 255, 255, 128);
  strokeWeight(80);

  ellipse(width/2, height/2, rad5*2, rad5*2);
  
  if (rad5 > 200)
  {
    rad5 = 0;
  }
  else
  {
    rad5+=1;
  }
  


>>>>>>> Stashed changes
}

