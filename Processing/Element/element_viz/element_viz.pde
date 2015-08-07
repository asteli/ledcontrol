OPC opc;
int bpm;
int selectedSequence;
TapInput tapInput;
ThreeArcDesign threeArcDesign;
MovingConcentrics movingConcentrics;
WrappedText wrappedText;
Strobe strobe;

void setup()
{
  noStroke();
  size(400, 400);
  colorMode(HSB);
  background(50);
  selectedSequence = 0;

  // Connect to the local instance of fcserver
  opc = new OPC(this, "127.0.0.1", 7890);
  opc.showLocations(true);

  create_opc_leds();
  
  threeArcDesign = new ThreeArcDesign();
  movingConcentrics = new MovingConcentrics(650);
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
    default:
      threeArcDesign.run();
      break;
  }
}

void mouseClicked() {
  if(selectedSequence == 3) {
    tapInput.strobeTap(strobe);
  }
}

