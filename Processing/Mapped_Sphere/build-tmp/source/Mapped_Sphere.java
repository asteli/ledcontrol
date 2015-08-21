import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.net.*; 
import java.util.Arrays; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Mapped_Sphere extends PApplet {

class GenerateSpheremap {
 
  int cur_x = 0;
  int cur_y = 0;
  
  // constants
  float light_width = 25;
  int n_strand_lights_populated[] = { 6, 10, 14, 19, 22, 26, 26, 27, 27, 28, 30, 30, 29, 29, 27, 26 };
  int n_strands_possible = 24;
  int n_strands_populated = 16;
  float base_ring_step = 20;
  
  // variables we modify in our loops
  int cur_ring_rad;
  int cur_strand_lights_possible;
  int cur_strand_index;
  int cur_strand_light_index;
  int cur_opc_led_index = 0;
  
  float theta_start = 0.0f;
  float theta_step = 0.0f;
  float theta_h = 0.0f;
  
  GenerateSpheremap() {
    // try to get rid of these perhaps
  }
   
   public void generate() {
      for ( cur_strand_index = 0; cur_strand_index < n_strands_populated; cur_strand_index++ ) {
        // calculate current ring radius based on ring index, but don't let it be zero.
        cur_ring_rad = PApplet.parseInt( floor( base_ring_step + (cur_strand_index * base_ring_step) ) );
        
        // divide full circle ( 2PI ) by fractional number of positions possible in ring
        theta_step = 2 * PI /  ( ( cur_ring_rad * 2 * PI ) / light_width );
        
        // theta start = half the lights worth of theta step. times negative one.
        theta_start = -1.0f * theta_step * 0.5f * n_strand_lights_populated[cur_strand_index];
        
        
        // iterate over each of the populated LED position.
        // the python script that generated the simulation layout was indexed starting on 1 apparently.
        for ( cur_strand_light_index = 1; cur_strand_light_index <= (n_strand_lights_populated[cur_strand_index]); cur_strand_light_index++ ) { 
          // get segment theta
          theta_h = (theta_step * cur_strand_light_index) + theta_start; 
          
          cur_x = width/2  + PApplet.parseInt(floor(cur_ring_rad * sin(theta_h)));
          cur_y = height/2 + PApplet.parseInt(floor(cur_ring_rad * cos(theta_h)));
          
          // debug
          // println("cur_x:", cur_x, " cur_y:", cur_y, " thetastep:", theta_step, " opc_index:", cur_opc_led_index, " strand_light_index:", cur_strand_light_index);
          
          opc.led(cur_opc_led_index, cur_x, cur_y);
          
          cur_opc_led_index++;
        }  
      } // end generate_spheremap() 
    }
}
/*
 * Simple Open Pixel Control client for Processing,
 * designed to sample each LED's color from some point on the canvas.
 *
 * Micah Elizabeth Scott, 2013
 * This file is released into the public domain.
 */




public class OPC
{
  Socket socket;
  OutputStream output;
  String host;
  int port;

  int[] pixelLocations;
  byte[] packetData;
  byte firmwareConfig;
  String colorCorrection;
  boolean enableShowLocations;

  OPC(PApplet parent, String host, int port)
  {
    this.host = host;
    this.port = port;
    this.enableShowLocations = true;
    parent.registerDraw(this);
  }

  // Set the location of a single LED
  public void led(int index, int x, int y)  
  {
    // For convenience, automatically grow the pixelLocations array. We do want this to be an array,
    // instead of a HashMap, to keep draw() as fast as it can be.
    if (pixelLocations == null) {
      pixelLocations = new int[index + 1];
    } else if (index >= pixelLocations.length) {
      pixelLocations = Arrays.copyOf(pixelLocations, index + 1);
    }

    pixelLocations[index] = x + width * y;
  }
  
  // Set the location of several LEDs arranged in a strip.
  // Angle is in radians, measured clockwise from +X.
  // (x,y) is the center of the strip.
  public void ledStrip(int index, int count, float x, float y, float spacing, float angle, boolean reversed)
  {
    float s = sin(angle);
    float c = cos(angle);
    for (int i = 0; i < count; i++) {
      led(reversed ? (index + count - 1 - i) : (index + i),
        (int)(x + (i - (count-1)/2.0f) * spacing * c + 0.5f),
        (int)(y + (i - (count-1)/2.0f) * spacing * s + 0.5f));
    }
  }

  // Set the locations of a ring of LEDs. The center of the ring is at (x, y),
  // with "radius" pixels between the center and each LED. The first LED is at
  // the indicated angle, in radians, measured clockwise from +X.
  public void ledRing(int index, int count, float x, float y, float radius, float angle)
  {
    for (int i = 0; i < count; i++) {
      float a = angle + i * 2 * PI / count;
      led(index + i, (int)(x - radius * cos(a) + 0.5f),
        (int)(y - radius * sin(a) + 0.5f));
    }
  }

  // Set the location of several LEDs arranged in a grid. The first strip is
  // at 'angle', measured in radians clockwise from +X.
  // (x,y) is the center of the grid.
  public void ledGrid(int index, int stripLength, int numStrips, float x, float y,
               float ledSpacing, float stripSpacing, float angle, boolean zigzag)
  {
    float s = sin(angle + HALF_PI);
    float c = cos(angle + HALF_PI);
    for (int i = 0; i < numStrips; i++) {
      ledStrip(index + stripLength * i, stripLength,
        x + (i - (numStrips-1)/2.0f) * stripSpacing * c,
        y + (i - (numStrips-1)/2.0f) * stripSpacing * s, ledSpacing,
        angle, zigzag && (i % 2) == 1);
    }
  }

  // Set the location of 64 LEDs arranged in a uniform 8x8 grid.
  // (x,y) is the center of the grid.
  public void ledGrid8x8(int index, float x, float y, float spacing, float angle, boolean zigzag)
  {
    ledGrid(index, 8, 8, x, y, spacing, spacing, angle, zigzag);
  }

  // Should the pixel sampling locations be visible? This helps with debugging.
  // Showing locations is enabled by default. You might need to disable it if our drawing
  // is interfering with your processing sketch, or if you'd simply like the screen to be
  // less cluttered.
  public void showLocations(boolean enabled)
  {
    enableShowLocations = enabled;
  }
  
  // Enable or disable dithering. Dithering avoids the "stair-stepping" artifact and increases color
  // resolution by quickly jittering between adjacent 8-bit brightness levels about 400 times a second.
  // Dithering is on by default.
  public void setDithering(boolean enabled)
  {
    if (enabled)
      firmwareConfig &= ~0x01;
    else
      firmwareConfig |= 0x01;
    sendFirmwareConfigPacket();
  }

  // Enable or disable frame interpolation. Interpolation automatically blends between consecutive frames
  // in hardware, and it does so with 16-bit per channel resolution. Combined with dithering, this helps make
  // fades very smooth. Interpolation is on by default.
  public void setInterpolation(boolean enabled)
  {
    if (enabled)
      firmwareConfig &= ~0x02;
    else
      firmwareConfig |= 0x02;
    sendFirmwareConfigPacket();
  }

  // Put the Fadecandy onboard LED under automatic control. It blinks any time the firmware processes a packet.
  // This is the default configuration for the LED.
  public void statusLedAuto()
  {
    firmwareConfig &= 0x0C;
    sendFirmwareConfigPacket();
  }    

  // Manually turn the Fadecandy onboard LED on or off. This disables automatic LED control.
  public void setStatusLed(boolean on)
  {
    firmwareConfig |= 0x04;   // Manual LED control
    if (on)
      firmwareConfig |= 0x08;
    else
      firmwareConfig &= ~0x08;
    sendFirmwareConfigPacket();
  } 

  // Set the color correction parameters
  public void setColorCorrection(float gamma, float red, float green, float blue)
  {
    colorCorrection = "{ \"gamma\": " + gamma + ", \"whitepoint\": [" + red + "," + green + "," + blue + "]}";
    sendColorCorrectionPacket();
  }
  
  // Set custom color correction parameters from a string
  public void setColorCorrection(String s)
  {
    colorCorrection = s;
    sendColorCorrectionPacket();
  }

  // Send a packet with the current firmware configuration settings
  public void sendFirmwareConfigPacket()
  {
    if (output == null) {
      // We'll do this when we reconnect
      return;
    }
 
    byte[] packet = new byte[9];
    packet[0] = 0;          // Channel (reserved)
    packet[1] = (byte)0xFF; // Command (System Exclusive)
    packet[2] = 0;          // Length high byte
    packet[3] = 5;          // Length low byte
    packet[4] = 0x00;       // System ID high byte
    packet[5] = 0x01;       // System ID low byte
    packet[6] = 0x00;       // Command ID high byte
    packet[7] = 0x02;       // Command ID low byte
    packet[8] = firmwareConfig;

    try {
      output.write(packet);
    } catch (Exception e) {
      dispose();
    }
  }

  // Send a packet with the current color correction settings
  public void sendColorCorrectionPacket()
  {
    if (colorCorrection == null) {
      // No color correction defined
      return;
    }
    if (output == null) {
      // We'll do this when we reconnect
      return;
    }

    byte[] content = colorCorrection.getBytes();
    int packetLen = content.length + 4;
    byte[] header = new byte[8];
    header[0] = 0;          // Channel (reserved)
    header[1] = (byte)0xFF; // Command (System Exclusive)
    header[2] = (byte)(packetLen >> 8);
    header[3] = (byte)(packetLen & 0xFF);
    header[4] = 0x00;       // System ID high byte
    header[5] = 0x01;       // System ID low byte
    header[6] = 0x00;       // Command ID high byte
    header[7] = 0x01;       // Command ID low byte

    try {
      output.write(header);
      output.write(content);
    } catch (Exception e) {
      dispose();
    }
  }

  // Automatically called at the end of each draw().
  // This handles the automatic Pixel to LED mapping.
  // If you aren't using that mapping, this function has no effect.
  // In that case, you can call setPixelCount(), setPixel(), and writePixels()
  // separately.
  public void draw()
  {
    if (pixelLocations == null) {
      // No pixels defined yet
      return;
    }
 
    if (output == null) {
      // Try to (re)connect
      connect();
    }
    if (output == null) {
      return;
    }

    int numPixels = pixelLocations.length;
    int ledAddress = 4;

    setPixelCount(numPixels);
    loadPixels();

    for (int i = 0; i < numPixels; i++) {
      int pixelLocation = pixelLocations[i];
      int pixel = pixels[pixelLocation];

      packetData[ledAddress] = (byte)(pixel >> 16);
      packetData[ledAddress + 1] = (byte)(pixel >> 8);
      packetData[ledAddress + 2] = (byte)pixel;
      ledAddress += 3;

      if (enableShowLocations) {
        pixels[pixelLocation] = 0xFFFFFF ^ pixel;
      }
    }

    writePixels();

    if (enableShowLocations) {
      updatePixels();
    }
  }
  
  // Change the number of pixels in our output packet.
  // This is normally not needed; the output packet is automatically sized
  // by draw() and by setPixel().
  public void setPixelCount(int numPixels)
  {
    int numBytes = 3 * numPixels;
    int packetLen = 4 + numBytes;
    if (packetData == null || packetData.length != packetLen) {
      // Set up our packet buffer
      packetData = new byte[packetLen];
      packetData[0] = 0;  // Channel
      packetData[1] = 0;  // Command (Set pixel colors)
      packetData[2] = (byte)(numBytes >> 8);
      packetData[3] = (byte)(numBytes & 0xFF);
    }
  }
  
  // Directly manipulate a pixel in the output buffer. This isn't needed
  // for pixels that are mapped to the screen.
  public void setPixel(int number, int c)
  {
    int offset = 4 + number * 3;
    if (packetData == null || packetData.length < offset + 3) {
      setPixelCount(number + 1);
    }

    packetData[offset] = (byte) (c >> 16);
    packetData[offset + 1] = (byte) (c >> 8);
    packetData[offset + 2] = (byte) c;
  }
  
  // Read a pixel from the output buffer. If the pixel was mapped to the display,
  // this returns the value we captured on the previous frame.
  public int getPixel(int number)
  {
    int offset = 4 + number * 3;
    if (packetData == null || packetData.length < offset + 3) {
      return 0;
    }
    return (packetData[offset] << 16) | (packetData[offset + 1] << 8) | packetData[offset + 2];
  }

  // Transmit our current buffer of pixel values to the OPC server. This is handled
  // automatically in draw() if any pixels are mapped to the screen, but if you haven't
  // mapped any pixels to the screen you'll want to call this directly.
  public void writePixels()
  {
    if (packetData == null || packetData.length == 0) {
      // No pixel buffer
      return;
    }
    if (output == null) {
      // Try to (re)connect
      connect();
    }
    if (output == null) {
      return;
    }

    try {
      output.write(packetData);
    } catch (Exception e) {
      dispose();
    }
  }

  public void dispose()
  {
    // Destroy the socket. Called internally when we've disconnected.
    if (output != null) {
      println("Disconnected from OPC server");
    }
    socket = null;
    output = null;
  }

  public void connect()
  {
    // Try to connect to the OPC server. This normally happens automatically in draw()
    try {
      socket = new Socket(host, port);
      socket.setTcpNoDelay(true);
      output = socket.getOutputStream();
      println("Connected to OPC server");
    } catch (ConnectException e) {
      dispose();
    } catch (IOException e) {
      dispose();
    }
    
    sendColorCorrectionPacket();
    sendFirmwareConfigPacket();
  }
}

OPC opc;
int bpm;
int selectedSequence;
GenerateSpheremap generateSpheremap;
TapInput tapInput;
ThreeArcDesign threeArcDesign;
MovingConcentrics movingConcentrics;
WrappedText wrappedText;
Strobe strobe;

public void setup() {
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

public void draw() {
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

public void mouseClicked() {
  if(selectedSequence == 3) {
    tapInput.strobeTap(strobe);
  }
}

class MovingConcentrics {
  float ellipseWidth;
  int numCircles = 3;
  int sphereWidth;
  MovingConcentrics(int ew) {
    sphereWidth = ew;
    ellipseWidth = sphereWidth;
  }
  
  public void run() {
    for(int i = 0; i < numCircles; i++) {
      generateCirclePairWithAlphaAndOffset(255 - (i*110), (i*40));
    }
    if(ellipseWidth > 0) {
      ellipseWidth -= 15;
    } else {
      ellipseWidth = sphereWidth;
    }
  }
  
  public void manualCircleSet() {
    
  }
  
  public void generateCirclePairWithAlphaAndOffset(int alpha, int offSet) {
    noFill();
    stroke(alpha, 255);
    strokeWeight(20);
    if(offSet == 0) {
      ellipse(width/2, height/2, ellipseWidth + offSet, ellipseWidth);
    } else {
      ellipse(width/2, height/2, ellipseWidth + offSet, ellipseWidth + offSet);
      ellipse(width/2, height/2, ellipseWidth - offSet, ellipseWidth - offSet);
    }
  }
}

class Strobe {
  float avgInterval = 1.0f;
  IntList intervalMillis = new IntList();
  int prevTime = 0;
  int strobeTime = 0;
  boolean pulse = true;
  Strobe() {
  }  
  
  public void tap() {
    if(prevTime == 0) {
      prevTime = millis();
      println("added millis 0");
    } else {
      intervalMillis.append(millis() - prevTime);
      prevTime = millis();
      println("had millis");
      calculateAverage();
    }
  }
  
  public void calculateAverage() {
    float total = 0;
    for(int i = 0; i < intervalMillis.size(); i++) {
      total = total + intervalMillis.get(i);
    }
    avgInterval = total / intervalMillis.size();
    println("avgInterval", avgInterval);
    println(intervalMillis.get(intervalMillis.size() - 1));
  }

  public void reset() {
    avgInterval = 1.0f;
    IntList zeroList = new IntList();
    intervalMillis = zeroList;
    strobeTime = 0;
  }

  public void setStartTime() {
    if(strobeTime == 0) {
      strobeTime = millis();
    }
  }
  
  public void pulseLoop() {
   if(pulse == true) {
      if(millis() > strobeTime + avgInterval) {
        spawnEllipse();
        strobeTime = millis();
      }
    }
  }

  public void spawnEllipse() {
    ellipse(width/2, height/2, 650, 650);
  }  
}

class TapInput {
  TapInput() {
  }
  
  public void strobeTap(Strobe strobe) {
    strobe.tap();
  }
}
class ThreeArcDesign {
 
  int hue_1 = 0;
  float theta_1 = 0;
  int hue_2 = 0;
  float theta_2 = 0;
  int hue_3 = 0;
  float theta_3 = 0;
  
 ThreeArcDesign() {
 }
 
 public void run() {
    if (hue_1 > 0) {
      hue_1 -= 3;
    } else {
      hue_1 = 255;
    }
    
    if (theta_1 > 0.05f) {
      theta_1 -= 0.05f;
    } else {
      theta_1 = TWO_PI;
    }
    fill(hue_1, 255, 250, 140);
    arc(width/2, height/2, width, height, theta_1, (theta_1 + 0.5f) );
    if (hue_2 < 252) {
      hue_2 += 1;
    } else {
      hue_2 = 0;
    }
    
    if (theta_2 < TWO_PI) {
      theta_2 += 0.1f;
    } else {
      theta_2 = 0;
    }
    fill(hue_3, 255, 250, 140);
    arc(width/2, height/2, width, height, theta_2, (theta_2 + 0.5f) );
  
    if (hue_3 < 255) {
      hue_3 += 5;
    } else {
      hue_3 = 0;
    }
    
    if (theta_3 < TWO_PI) {
      theta_3 += 0.02f;
    } else {
      theta_3 = 0;
    }  
    fill(hue_3, 255, 250, 80);
    arc(width/2, height/2, width, height, theta_3, (theta_3 + 1) );
    ellipse(mouseX, mouseY, 100, 100); 
   }
}
class WrappedText {
  //pfFont font;
  String wrapWord = "";
  char[] wrapWordChars;
  float rotateText = 0;
  WrappedText(String word) {
    wrapWord = word + "  ";
    wrapWordChars = new char[wrapWord.length()];
    wrapWordChars = getWrapWordChars();
    //font = loadFont("AvenirNext-Bold-250.vlw");
  }
  
  public char[] getWrapWordChars() {
    char[] chars = new char[wrapWord.length()];
    for(int i = (wrapWord.length() - 1); i >= 0; i--) {
      chars[i] = wrapWord.charAt(i);
    }
    return chars;
  }
  
  public void animateLetters(float speed) {
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Mapped_Sphere" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
