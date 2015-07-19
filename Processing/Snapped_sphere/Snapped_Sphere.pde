OPC opc;

void setup()
{
  size(800, 200);

  // Connect to the local instance of fcserver
  opc = new OPC(this, "127.0.0.1", 7890);

  // Map one 64-LED strip to the center of the window
  opc.led(0, width/2, height/2);
}

void draw()
{
  background(0);

}
