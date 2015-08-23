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


int rad1 = 12;
int rad2 = 75;
int rad3 = 150;
int rad4 = 225;
int rad5 = 300;

public void draw()
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
  
  if (theta_1 > 0.05f)
  {
    theta_1 -= 0.05f;
  }
  else
  {
    theta_1 = TWO_PI;
  }

  
  fill(100, 255, 250, 255);
  
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
  
  
  fill(hue_3, 255, 250, 255);
  
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
    theta_3 += 0.1f;
  }
  else
  {
    theta_3 = 0;
  }
  
  
//  fill(hue_3, 255, 250, 255);
  
  fill(hue_3, 0, 255, 255); 

  arc(width/2, height/2, width, height, theta_3, (theta_3 + 0.3f ));




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
  }


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
opc.led(0, 192, 229);
 opc.led(1, 174, 215);
 opc.led(2, 170, 192);
 opc.led(3, 184, 174);
 opc.led(4, 207, 170);
 opc.led(5, 225, 184);
 opc.led(6, 229, 207);
 opc.led(7, 215, 225);
 opc.led(8, 191, 238);
 opc.led(9, 175, 231);
 opc.led(10, 163, 217);
 opc.led(11, 160, 200);
 opc.led(12, 163, 182);
 opc.led(13, 175, 168);
 opc.led(14, 191, 161);
 opc.led(15, 208, 161);
 opc.led(16, 224, 168);
 opc.led(17, 236, 182);
 opc.led(18, 240, 200);
 opc.led(19, 236, 217);
 opc.led(20, 224, 231);
 opc.led(21, 208, 238);
 opc.led(22, 198, 249);
 opc.led(23, 183, 247);
 opc.led(24, 169, 239);
 opc.led(25, 158, 228);
 opc.led(26, 151, 213);
 opc.led(27, 150, 198);
 opc.led(28, 152, 183);
 opc.led(29, 160, 169);
 opc.led(30, 171, 158);
 opc.led(31, 186, 151);
 opc.led(32, 201, 150);
 opc.led(33, 216, 152);
 opc.led(34, 230, 160);
 opc.led(35, 241, 171);
 opc.led(36, 248, 186);
 opc.led(37, 249, 201);
 opc.led(38, 247, 216);
 opc.led(39, 239, 230);
 opc.led(40, 228, 241);
 opc.led(41, 213, 248);
 opc.led(42, 202, 259);
 opc.led(43, 188, 258);
 opc.led(44, 174, 254);
 opc.led(45, 162, 246);
 opc.led(46, 152, 236);
 opc.led(47, 144, 223);
 opc.led(48, 140, 210);
 opc.led(49, 140, 195);
 opc.led(50, 142, 181);
 opc.led(51, 149, 168);
 opc.led(52, 158, 157);
 opc.led(53, 169, 148);
 opc.led(54, 182, 142);
 opc.led(55, 197, 140);
 opc.led(56, 211, 141);
 opc.led(57, 225, 145);
 opc.led(58, 237, 153);
 opc.led(59, 247, 163);
 opc.led(60, 255, 176);
 opc.led(61, 259, 189);
 opc.led(62, 259, 204);
 opc.led(63, 257, 218);
 opc.led(64, 250, 231);
 opc.led(65, 241, 242);
 opc.led(66, 230, 251);
 opc.led(67, 217, 257);
 opc.led(68, 208, 269);
 opc.led(69, 194, 269);
 opc.led(70, 180, 267);
 opc.led(71, 167, 261);
 opc.led(72, 155, 254);
 opc.led(73, 145, 243);
 opc.led(74, 137, 232);
 opc.led(75, 132, 218);
 opc.led(76, 130, 204);
 opc.led(77, 130, 190);
 opc.led(78, 133, 177);
 opc.led(79, 139, 164);
 opc.led(80, 148, 152);
 opc.led(81, 158, 143);
 opc.led(82, 171, 136);
 opc.led(83, 184, 131);
 opc.led(84, 198, 130);
 opc.led(85, 212, 131);
 opc.led(86, 226, 135);
 opc.led(87, 238, 141);
 opc.led(88, 249, 150);
 opc.led(89, 258, 161);
 opc.led(90, 265, 174);
 opc.led(91, 268, 187);
 opc.led(92, 269, 202);
 opc.led(93, 268, 216);
 opc.led(94, 263, 229);
 opc.led(95, 256, 241);
 opc.led(96, 246, 252);
 opc.led(97, 235, 260);
 opc.led(98, 222, 266);
 opc.led(99, 278, 216);
 opc.led(100, 274, 229);
 opc.led(101, 268, 241);
 opc.led(102, 260, 252);
 opc.led(103, 250, 262);
 opc.led(104, 239, 269);
 opc.led(105, 226, 275);
 opc.led(106, 213, 278);
 opc.led(107, 199, 279);
 opc.led(108, 185, 278);
 opc.led(109, 172, 275);
 opc.led(110, 160, 269);
 opc.led(111, 149, 261);
 opc.led(112, 139, 252);
 opc.led(113, 131, 240);
 opc.led(114, 125, 228);
 opc.led(115, 112, 220);
 opc.led(116, 116, 233);
 opc.led(117, 122, 246);
 opc.led(118, 130, 257);
 opc.led(119, 140, 267);
 opc.led(120, 151, 275);
 opc.led(121, 163, 282);
 opc.led(122, 176, 286);
 opc.led(123, 190, 289);
 opc.led(124, 204, 289);
 opc.led(125, 217, 288);
 opc.led(126, 231, 284);
 opc.led(127, 243, 278);
 opc.led(128, 255, 271);
 opc.led(129, 265, 261);
 opc.led(130, 274, 251);
 opc.led(131, 281, 239);
 opc.led(132, 293, 235);
 opc.led(133, 287, 248);
 opc.led(134, 280, 259);
 opc.led(135, 270, 270);
 opc.led(136, 260, 279);
 opc.led(137, 248, 287);
 opc.led(138, 235, 293);
 opc.led(139, 221, 297);
 opc.led(140, 208, 299);
 opc.led(141, 193, 299);
 opc.led(142, 179, 297);
 opc.led(143, 166, 294);
 opc.led(144, 153, 288);
 opc.led(145, 141, 281);
 opc.led(146, 130, 271);
 opc.led(147, 121, 261);
 opc.led(148, 113, 249);
 opc.led(149, 107, 237);
 opc.led(150, 102, 223);
 opc.led(151, 96, 236);
 opc.led(152, 102, 250);
 opc.led(153, 109, 262);
 opc.led(154, 118, 274);
 opc.led(155, 129, 284);
 opc.led(156, 140, 292);
 opc.led(157, 153, 299);
 opc.led(158, 167, 305);
 opc.led(159, 181, 308);
 opc.led(160, 196, 309);
 opc.led(161, 210, 309);
 opc.led(162, 225, 307);
 opc.led(163, 239, 302);
 opc.led(164, 252, 296);
 opc.led(165, 264, 289);
 opc.led(166, 275, 279);
 opc.led(167, 285, 268);
 opc.led(168, 294, 256);
 opc.led(169, 300, 244);
 opc.led(170, 317, 225);
 opc.led(171, 312, 240);
 opc.led(172, 306, 254);
 opc.led(173, 299, 267);
 opc.led(174, 289, 279);
 opc.led(175, 278, 290);
 opc.led(176, 266, 299);
 opc.led(177, 253, 307);
 opc.led(178, 239, 313);
 opc.led(179, 224, 317);
 opc.led(180, 209, 319);
 opc.led(181, 194, 319);
 opc.led(182, 179, 318);
 opc.led(183, 164, 314);
 opc.led(184, 150, 309);
 opc.led(185, 136, 301);
 opc.led(186, 124, 293);
 opc.led(187, 113, 282);
 opc.led(188, 103, 271);
 opc.led(189, 95, 258);
 opc.led(190, 88, 244);
 opc.led(191, 76, 240);
 opc.led(192, 82, 255);
 opc.led(193, 89, 269);
 opc.led(194, 99, 282);
 opc.led(195, 110, 294);
 opc.led(196, 122, 304);
 opc.led(197, 136, 313);
 opc.led(198, 150, 320);
 opc.led(199, 165, 325);
 opc.led(200, 181, 328);
 opc.led(201, 197, 329);
 opc.led(202, 213, 329);
 opc.led(203, 229, 326);
 opc.led(204, 245, 321);
 opc.led(205, 259, 315);
 opc.led(206, 273, 307);
 opc.led(207, 286, 297);
 opc.led(208, 297, 285);
 opc.led(209, 307, 272);
 opc.led(210, 315, 259);
 opc.led(211, 322, 244);
 opc.led(212, 339, 203);
 opc.led(213, 338, 220);
 opc.led(214, 334, 237);
 opc.led(215, 329, 254);
 opc.led(216, 321, 269);
 opc.led(217, 312, 283);
 opc.led(218, 300, 296);
 opc.led(219, 288, 308);
 opc.led(220, 274, 318);
 opc.led(221, 259, 326);
 opc.led(222, 243, 333);
 opc.led(223, 226, 337);
 opc.led(224, 209, 339);
 opc.led(225, 192, 339);
 opc.led(226, 175, 337);
 opc.led(227, 158, 333);
 opc.led(228, 142, 327);
 opc.led(229, 127, 319);
 opc.led(230, 112, 309);
 opc.led(231, 100, 298);
 opc.led(232, 88, 285);
 opc.led(233, 79, 270);
 opc.led(234, 71, 255);
 opc.led(235, 65, 239);
 opc.led(236, 61, 222);
 opc.led(237, 60, 205);
 opc.led(238, 53, 166);
 opc.led(239, 50, 184);
 opc.led(240, 50, 203);
 opc.led(241, 51, 221);
 opc.led(242, 55, 240);
 opc.led(243, 61, 257);
 opc.led(244, 69, 274);
 opc.led(245, 79, 289);
 opc.led(246, 92, 304);
 opc.led(247, 105, 316);
 opc.led(248, 120, 327);
 opc.led(249, 137, 336);
 opc.led(250, 154, 342);
 opc.led(251, 172, 347);
 opc.led(252, 191, 349);
 opc.led(253, 209, 349);
 opc.led(254, 228, 347);
 opc.led(255, 246, 342);
 opc.led(256, 263, 335);
 opc.led(257, 279, 327);
 opc.led(258, 294, 316);
 opc.led(259, 308, 303);
 opc.led(260, 320, 289);
 opc.led(261, 330, 273);
 opc.led(262, 338, 256);
 opc.led(263, 344, 239);
 opc.led(264, 348, 221);
 opc.led(265, 349, 202);
 opc.led(266, 349, 184);
 opc.led(267, 346, 165);
 opc.led(268, 354, 156);
 opc.led(269, 358, 176);
 opc.led(270, 359, 196);
 opc.led(271, 359, 217);
 opc.led(272, 355, 237);
 opc.led(273, 349, 256);
 opc.led(274, 341, 275);
 opc.led(275, 330, 292);
 opc.led(276, 317, 308);
 opc.led(277, 302, 322);
 opc.led(278, 286, 334);
 opc.led(279, 268, 344);
 opc.led(280, 249, 352);
 opc.led(281, 230, 357);
 opc.led(282, 210, 359);
 opc.led(283, 189, 359);
 opc.led(284, 169, 357);
 opc.led(285, 149, 351);
 opc.led(286, 131, 344);
 opc.led(287, 113, 334);
 opc.led(288, 96, 322);
 opc.led(289, 82, 308);
 opc.led(290, 69, 292);
 opc.led(291, 58, 275);
 opc.led(292, 50, 256);
 opc.led(293, 44, 237);
 opc.led(294, 40, 217);
 opc.led(295, 40, 196);
 opc.led(296, 41, 176);
 opc.led(297, 45, 129);
 opc.led(298, 37, 150);
 opc.led(299, 32, 172);
 opc.led(300, 30, 195);
 opc.led(301, 30, 217);
 opc.led(302, 34, 240);
 opc.led(303, 41, 261);
 opc.led(304, 51, 282);
 opc.led(305, 63, 301);
 opc.led(306, 77, 318);
 opc.led(307, 94, 333);
 opc.led(308, 113, 346);
 opc.led(309, 133, 356);
 opc.led(310, 154, 363);
 opc.led(311, 176, 368);
 opc.led(312, 199, 369);
 opc.led(313, 221, 368);
 opc.led(314, 243, 364);
 opc.led(315, 265, 356);
 opc.led(316, 285, 346);
 opc.led(317, 304, 334);
 opc.led(318, 321, 319);
 opc.led(319, 335, 302);
 opc.led(320, 348, 283);
 opc.led(321, 357, 262);
 opc.led(322, 364, 241);
 opc.led(323, 368, 219);
 opc.led(324, 358, 115);
 opc.led(325, 369, 138);
 opc.led(326, 376, 162);
 opc.led(327, 379, 187);
 opc.led(328, 379, 213);
 opc.led(329, 375, 238);
 opc.led(330, 368, 262);
 opc.led(331, 358, 286);
 opc.led(332, 344, 307);
 opc.led(333, 327, 326);
 opc.led(334, 308, 343);
 opc.led(335, 287, 357);
 opc.led(336, 264, 368);
 opc.led(337, 239, 375);
 opc.led(338, 214, 379);
 opc.led(339, 189, 379);
 opc.led(340, 164, 376);
 opc.led(341, 139, 369);
 opc.led(342, 116, 359);
 opc.led(343, 94, 345);
 opc.led(344, 75, 329);
 opc.led(345, 58, 310);
 opc.led(346, 43, 289);
 opc.led(347, 32, 266);
 opc.led(348, 25, 242);
 opc.led(349, 20, 217);
 opc.led(350, 20, 191);
 opc.led(351, 23, 166);
 opc.led(352, 29, 142);
 opc.led(353, 39, 118);

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
