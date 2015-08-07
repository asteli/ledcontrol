
void setup()
{
  size(800, 800, P3D);
  noStroke();
  colorMode(HSB);
  background(0);
}

int hue_1 = 0;
float theta_1 = 0;
int hue_2 = 0;
float theta_2 = 0;

void draw()
{
 
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
  
  
  fill(hue_1, 255, 250, 140);
  
  arc(width/2, height/2, width, height, theta_1, (theta_1 + 0.1) );
  
  
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
  
  
  fill(hue_2, 255, 250, 140);
  
  arc(width/2, height/2, width/2, height/2, theta_2, (theta_2 + 0.1) );
}
