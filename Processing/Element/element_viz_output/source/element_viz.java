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

public class element_viz extends PApplet {

OPC opc;

public void setup()
{
  noStroke();
  size(400, 400);
  colorMode(HSB);
  background(50);

  // Connect to the local instance of fcserver
  opc = new OPC(this, "127.0.0.1", 7890);
  opc.showLocations(true);

  create_opc_leds();

}

int hue_1 = 0;
float theta_1 = 0;
int hue_2 = 0;
float theta_2 = 0;
int hue_3 = 0;
float theta_3 = 0;


int rad1 = 0;
int rad2 = 20;
int rad3 = 40;
int rad4 = 30;
int rad5 = 50;

public void draw()
{
  background(5, 5, 5);
  
  
  if (hue_1 > 0)
  {
    hue_1 -= 3;
  }
  else
  {
    hue_1 = 255;
  }
  
  if (theta_1 > 0.05f)
  {
    theta_1 -= 0.05f;
  }
  else
  {
    theta_1 = TWO_PI;
  }

  
  fill(100, 255, 250, 140);
  
  arc(width/2, height/2, width, height, theta_1, (theta_1 + 0.4f) );
  

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
    theta_2 += 0.1f;
  }
  else
  {
    theta_2 = 0;
  }
  
  
  fill(hue_3, 255, 250, 140);
  
  arc(width/2, height/2, width, height, theta_2, (theta_2 + 0.5f) );

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
    theta_3 += 0.02f;
  }
  else
  {
    theta_3 = 0;
  }
  
  
  fill(hue_3, 255, 250, 80);
  
  arc(width/2, height/2, width, height, theta_3, (theta_3 + 1) );


  // ellipse(mouseX, mouseY, 100, 100);


  noFill();
  
  stroke(255, 255, 128);
  strokeWeight(10);

  ellipse(width/2, height/2, rad1*2, rad1*2);
  
  if (rad1 > 60)
  {
    rad1 = 0;
  }
  else
  {
    rad1++;
  }


  stroke(24, 255, 255, 128);
  strokeWeight(10);

  ellipse(width/2, height/2, rad2*2, rad2*2);
  
  if (rad2 > 60)
  {
    rad2 = 0;
  }
  else
  {
    rad2++;
  }  
  
  
  stroke(128, 255, 255, 128);
  strokeWeight(10);

  ellipse(width/2, height/2, rad3*2, rad3*2);
  
  if (rad3 > 60)
  {
    rad3 = 0;
  }
  else
  {
    rad3++;
  }

  stroke(64, 255, 255, 128);
  strokeWeight(10);

  ellipse(width/2, height/2, rad4*2, rad4*2);
  
  if (rad4 > 60)
  {
    rad4 = 0;
  }
  else
  {
    rad4++;
  }


  stroke(200, 255, 255, 128);
  strokeWeight(10);

  ellipse(width/2, height/2, rad5*2, rad5*2);
  
  if (rad5 > 60)
  {
    rad5 = 0;
  }
  else
  {
    rad5++;
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

public void create_opc_leds()
{
opc.led(0, 200, 230);
 opc.led(1, 223, 218);
 opc.led(2, 229, 193);
 opc.led(3, 213, 172);
 opc.led(4, 186, 172);
 opc.led(5, 170, 193);
 opc.led(6, 176, 218);
 opc.led(7, 200, 240);
 opc.led(8, 216, 236);
 opc.led(9, 229, 226);
 opc.led(10, 238, 212);
 opc.led(11, 239, 195);
 opc.led(12, 234, 180);
 opc.led(13, 223, 167);
 opc.led(14, 208, 160);
 opc.led(15, 191, 160);
 opc.led(16, 176, 167);
 opc.led(17, 165, 179);
 opc.led(18, 160, 195);
 opc.led(19, 161, 212);
 opc.led(20, 170, 226);
 opc.led(21, 183, 236);
 opc.led(22, 200, 250);
 opc.led(23, 214, 247);
 opc.led(24, 228, 241);
 opc.led(25, 239, 231);
 opc.led(26, 246, 218);
 opc.led(27, 249, 203);
 opc.led(28, 248, 188);
 opc.led(29, 243, 175);
 opc.led(30, 234, 163);
 opc.led(31, 221, 154);
 opc.led(32, 207, 150);
 opc.led(33, 192, 150);
 opc.led(34, 178, 154);
 opc.led(35, 165, 163);
 opc.led(36, 156, 175);
 opc.led(37, 151, 188);
 opc.led(38, 150, 203);
 opc.led(39, 153, 218);
 opc.led(40, 160, 231);
 opc.led(41, 171, 241);
 opc.led(42, 185, 247);
 opc.led(43, 200, 260);
 opc.led(44, 213, 258);
 opc.led(45, 226, 253);
 opc.led(46, 238, 245);
 opc.led(47, 248, 235);
 opc.led(48, 255, 223);
 opc.led(49, 259, 210);
 opc.led(50, 259, 196);
 opc.led(51, 257, 182);
 opc.led(52, 251, 170);
 opc.led(53, 243, 158);
 opc.led(54, 232, 149);
 opc.led(55, 220, 143);
 opc.led(56, 206, 140);
 opc.led(57, 193, 140);
 opc.led(58, 179, 143);
 opc.led(59, 167, 149);
 opc.led(60, 156, 158);
 opc.led(61, 148, 169);
 opc.led(62, 142, 182);
 opc.led(63, 140, 196);
 opc.led(64, 140, 210);
 opc.led(65, 144, 223);
 opc.led(66, 151, 235);
 opc.led(67, 161, 245);
 opc.led(68, 173, 253);
 opc.led(69, 186, 258);
 opc.led(70, 200, 270);
 opc.led(71, 213, 268);
 opc.led(72, 226, 264);
 opc.led(73, 238, 258);
 opc.led(74, 249, 249);
 opc.led(75, 258, 238);
 opc.led(76, 264, 226);
 opc.led(77, 268, 213);
 opc.led(78, 270, 200);
 opc.led(79, 268, 186);
 opc.led(80, 264, 173);
 opc.led(81, 258, 161);
 opc.led(82, 249, 150);
 opc.led(83, 238, 141);
 opc.led(84, 226, 135);
 opc.led(85, 213, 131);
 opc.led(86, 200, 130);
 opc.led(87, 186, 131);
 opc.led(88, 173, 135);
 opc.led(89, 161, 141);
 opc.led(90, 150, 150);
 opc.led(91, 141, 161);
 opc.led(92, 135, 173);
 opc.led(93, 131, 186);
 opc.led(94, 130, 200);
 opc.led(95, 131, 213);
 opc.led(96, 135, 226);
 opc.led(97, 141, 238);
 opc.led(98, 150, 249);
 opc.led(99, 161, 258);
 opc.led(100, 173, 264);
 opc.led(101, 186, 268);
 opc.led(102, 279, 193);
 opc.led(103, 279, 206);
 opc.led(104, 277, 220);
 opc.led(105, 272, 233);
 opc.led(106, 265, 245);
 opc.led(107, 257, 255);
 opc.led(108, 246, 264);
 opc.led(109, 235, 271);
 opc.led(110, 222, 276);
 opc.led(111, 208, 279);
 opc.led(112, 195, 279);
 opc.led(113, 181, 277);
 opc.led(114, 168, 273);
 opc.led(115, 156, 267);
 opc.led(116, 145, 258);
 opc.led(117, 136, 248);
 opc.led(118, 129, 237);
 opc.led(119, 123, 224);
 opc.led(120, 120, 211);
 opc.led(121, 120, 197);
 opc.led(122, 110, 201);
 opc.led(123, 111, 215);
 opc.led(124, 114, 228);
 opc.led(125, 119, 241);
 opc.led(126, 127, 252);
 opc.led(127, 136, 263);
 opc.led(128, 146, 272);
 opc.led(129, 158, 279);
 opc.led(130, 171, 285);
 opc.led(131, 184, 288);
 opc.led(132, 198, 289);
 opc.led(133, 211, 289);
 opc.led(134, 225, 286);
 opc.led(135, 238, 281);
 opc.led(136, 250, 274);
 opc.led(137, 261, 266);
 opc.led(138, 270, 255);
 opc.led(139, 278, 244);
 opc.led(140, 284, 231);
 opc.led(141, 288, 218);
 opc.led(142, 289, 204);
 opc.led(143, 299, 207);
 opc.led(144, 297, 221);
 opc.led(145, 293, 234);
 opc.led(146, 288, 247);
 opc.led(147, 280, 259);
 opc.led(148, 271, 270);
 opc.led(149, 260, 279);
 opc.led(150, 248, 287);
 opc.led(151, 236, 293);
 opc.led(152, 222, 297);
 opc.led(153, 208, 299);
 opc.led(154, 194, 299);
 opc.led(155, 180, 298);
 opc.led(156, 166, 294);
 opc.led(157, 154, 288);
 opc.led(158, 141, 281);
 opc.led(159, 131, 272);
 opc.led(160, 121, 262);
 opc.led(161, 113, 250);
 opc.led(162, 107, 237);
 opc.led(163, 102, 224);
 opc.led(164, 100, 210);
 opc.led(165, 90, 210);
 opc.led(166, 92, 225);
 opc.led(167, 97, 239);
 opc.led(168, 103, 252);
 opc.led(169, 111, 264);
 opc.led(170, 120, 275);
 opc.led(171, 131, 285);
 opc.led(172, 143, 294);
 opc.led(173, 156, 300);
 opc.led(174, 169, 305);
 opc.led(175, 183, 308);
 opc.led(176, 198, 309);
 opc.led(177, 213, 309);
 opc.led(178, 227, 306);
 opc.led(179, 241, 301);
 opc.led(180, 254, 295);
 opc.led(181, 266, 287);
 opc.led(182, 277, 277);
 opc.led(183, 287, 266);
 opc.led(184, 295, 254);
 opc.led(185, 301, 241);
 opc.led(186, 306, 227);
 opc.led(187, 309, 213);
 opc.led(188, 319, 211);
 opc.led(189, 316, 226);
 opc.led(190, 312, 241);
 opc.led(191, 306, 255);
 opc.led(192, 298, 268);
 opc.led(193, 289, 280);
 opc.led(194, 278, 291);
 opc.led(195, 266, 300);
 opc.led(196, 252, 307);
 opc.led(197, 238, 313);
 opc.led(198, 223, 317);
 opc.led(199, 208, 319);
 opc.led(200, 193, 319);
 opc.led(201, 178, 318);
 opc.led(202, 163, 314);
 opc.led(203, 149, 308);
 opc.led(204, 136, 301);
 opc.led(205, 123, 292);
 opc.led(206, 112, 282);
 opc.led(207, 102, 270);
 opc.led(208, 94, 257);
 opc.led(209, 88, 243);
 opc.led(210, 83, 229);
 opc.led(211, 80, 214);
 opc.led(212, 70, 201);
 opc.led(213, 71, 217);
 opc.led(214, 74, 233);
 opc.led(215, 79, 249);
 opc.led(216, 86, 263);
 opc.led(217, 95, 277);
 opc.led(218, 105, 289);
 opc.led(219, 117, 300);
 opc.led(220, 130, 309);
 opc.led(221, 144, 317);
 opc.led(222, 159, 323);
 opc.led(223, 175, 327);
 opc.led(224, 191, 329);
 opc.led(225, 207, 329);
 opc.led(226, 223, 327);
 opc.led(227, 238, 324);
 opc.led(228, 253, 318);
 opc.led(229, 268, 310);
 opc.led(230, 281, 301);
 opc.led(231, 293, 290);
 opc.led(232, 303, 278);
 opc.led(233, 312, 264);
 opc.led(234, 319, 250);
 opc.led(235, 325, 235);
 opc.led(236, 328, 219);
 opc.led(237, 329, 203);
 opc.led(238, 336, 170);
 opc.led(239, 339, 187);
 opc.led(240, 339, 205);
 opc.led(241, 338, 222);
 opc.led(242, 334, 238);
 opc.led(243, 328, 255);
 opc.led(244, 320, 270);
 opc.led(245, 311, 284);
 opc.led(246, 300, 297);
 opc.led(247, 287, 309);
 opc.led(248, 273, 319);
 opc.led(249, 258, 327);
 opc.led(250, 242, 333);
 opc.led(251, 225, 337);
 opc.led(252, 208, 339);
 opc.led(253, 191, 339);
 opc.led(254, 173, 337);
 opc.led(255, 157, 333);
 opc.led(256, 141, 327);
 opc.led(257, 126, 318);
 opc.led(258, 112, 308);
 opc.led(259, 99, 297);
 opc.led(260, 88, 284);
 opc.led(261, 78, 269);
 opc.led(262, 71, 254);
 opc.led(263, 65, 238);
 opc.led(264, 61, 221);
 opc.led(265, 60, 204);
 opc.led(266, 60, 187);
 opc.led(267, 63, 170);
 opc.led(268, 80, 109);
 opc.led(269, 69, 125);
 opc.led(270, 61, 142);
 opc.led(271, 55, 159);
 opc.led(272, 51, 177);
 opc.led(273, 50, 196);
 opc.led(274, 50, 215);
 opc.led(275, 53, 233);
 opc.led(276, 59, 251);
 opc.led(277, 66, 268);
 opc.led(278, 75, 284);
 opc.led(279, 87, 298);
 opc.led(280, 100, 312);
 opc.led(281, 115, 323);
 opc.led(282, 130, 333);
 opc.led(283, 147, 340);
 opc.led(284, 165, 346);
 opc.led(285, 184, 349);
 opc.led(286, 202, 349);
 opc.led(287, 221, 348);
 opc.led(288, 239, 344);
 opc.led(289, 257, 338);
 opc.led(290, 273, 330);
 opc.led(291, 289, 320);
 opc.led(292, 303, 308);
 opc.led(293, 316, 294);
 opc.led(294, 327, 279);
 opc.led(295, 335, 263);
 opc.led(296, 342, 246);
 opc.led(297, 347, 228);
 opc.led(298, 349, 209);
 opc.led(299, 349, 191);
 opc.led(300, 347, 172);
 opc.led(301, 342, 154);
 opc.led(302, 336, 137);
 opc.led(303, 327, 120);
 opc.led(304, 316, 105);
 opc.led(305, 334, 112);
 opc.led(306, 344, 130);
 opc.led(307, 351, 149);
 opc.led(308, 356, 168);
 opc.led(309, 359, 188);
 opc.led(310, 359, 209);
 opc.led(311, 357, 229);
 opc.led(312, 352, 249);
 opc.led(313, 344, 268);
 opc.led(314, 334, 285);
 opc.led(315, 322, 302);
 opc.led(316, 308, 317);
 opc.led(317, 293, 330);
 opc.led(318, 276, 340);
 opc.led(319, 257, 349);
 opc.led(320, 238, 355);
 opc.led(321, 218, 358);
 opc.led(322, 197, 359);
 opc.led(323, 177, 358);
 opc.led(324, 157, 354);
 opc.led(325, 138, 347);
 opc.led(326, 120, 338);
 opc.led(327, 103, 327);
 opc.led(328, 87, 314);
 opc.led(329, 74, 298);
 opc.led(330, 62, 282);
 opc.led(331, 53, 264);
 opc.led(332, 46, 244);
 opc.led(333, 41, 225);
 opc.led(334, 40, 204);
 opc.led(335, 40, 184);
 opc.led(336, 44, 164);
 opc.led(337, 49, 144);
 opc.led(338, 57, 126);
 opc.led(339, 68, 108);
 opc.led(340, 44, 131);
 opc.led(341, 36, 152);
 opc.led(342, 31, 174);
 opc.led(343, 30, 196);
 opc.led(344, 31, 219);
 opc.led(345, 35, 241);
 opc.led(346, 42, 263);
 opc.led(347, 51, 283);
 opc.led(348, 64, 302);
 opc.led(349, 79, 319);
 opc.led(350, 95, 334);
 opc.led(351, 114, 347);
 opc.led(352, 134, 357);
 opc.led(353, 156, 364);
 opc.led(354, 178, 368);
 opc.led(355, 200, 369);
 opc.led(356, 223, 368);
 opc.led(357, 245, 363);
 opc.led(358, 266, 356);
 opc.led(359, 286, 346);
 opc.led(360, 305, 333);
 opc.led(361, 322, 318);
 opc.led(362, 336, 300);
 opc.led(363, 348, 281);
 opc.led(364, 358, 261);
 opc.led(365, 365, 239);
 opc.led(366, 369, 217);
 opc.led(367, 369, 195);
 opc.led(368, 367, 172);
 opc.led(369, 362, 150);
 opc.led(370, 354, 129);
 opc.led(371, 369, 139);
 opc.led(372, 376, 164);
 opc.led(373, 379, 189);
 opc.led(374, 379, 215);
 opc.led(375, 375, 240);
 opc.led(376, 368, 264);
 opc.led(377, 357, 287);
 opc.led(378, 343, 308);
 opc.led(379, 326, 327);
 opc.led(380, 307, 344);
 opc.led(381, 285, 358);
 opc.led(382, 262, 368);
 opc.led(383, 238, 375);
 opc.led(384, 213, 379);
 opc.led(385, 187, 379);
 opc.led(386, 162, 376);
 opc.led(387, 137, 368);
 opc.led(388, 114, 358);
 opc.led(389, 93, 344);
 opc.led(390, 73, 328);
 opc.led(391, 57, 309);
 opc.led(392, 43, 288);
 opc.led(393, 32, 265);
 opc.led(394, 24, 240);
 opc.led(395, 20, 215);
 opc.led(396, 20, 190);
 opc.led(397, 23, 165);
 opc.led(398, 30, 140);
 opc.led(399, 10, 201);
 opc.led(400, 12, 231);
 opc.led(401, 19, 259);
 opc.led(402, 30, 286);
 opc.led(403, 46, 311);
 opc.led(404, 64, 333);
 opc.led(405, 87, 352);
 opc.led(406, 111, 368);
 opc.led(407, 138, 379);
 opc.led(408, 166, 387);
 opc.led(409, 196, 389);
 opc.led(410, 225, 388);
 opc.led(411, 253, 382);
 opc.led(412, 281, 371);
 opc.led(413, 306, 357);
 opc.led(414, 329, 339);
 opc.led(415, 349, 317);
 opc.led(416, 365, 293);
 opc.led(417, 377, 266);
 opc.led(418, 385, 238);
 opc.led(419, 389, 209);

}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "element_viz" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
