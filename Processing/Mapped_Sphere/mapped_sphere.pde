OPC opc;
int bpm;
int selectedSequence;
GenerateSpheremap generateSpheremap;
TapInput tapInput;
ThreeArcDesign threeArcDesign;
MovingConcentrics movingConcentrics;
WrappedText wrappedText;
Strobe strobe;

void setup() {
  noStroke();
  size(800, 800, P3D);
  colorMode(HSB);
  background(0);
  selectedSequence = 3;

  // Connect to the local instance of fcserver
  opc = new OPC(this, "127.0.0.1", 7890);
  opc.showLocations(true);
  
  //tapInput = new TapInput();
  
  generateSpheremap = new GenerateSpheremap();
  generateSpheremap.generate();
  
  threeArcDesign = new ThreeArcDesign();
  movingConcentrics = new MovingConcentrics(650);
  wrappedText = new WrappedText("SING");
  strobe = new Strobe();
  tapInput = new TapInput();
}

void draw()
{
  background(30);
  
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

