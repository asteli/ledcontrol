class Strobe {
  float avgInterval = 1.0;
  IntList intervalMillis = new IntList();
  int prevTime = 0;
  int strobeTime = 0;
  boolean pulse = true;
  Strobe() {
  }  
  
  void tap() {
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
  
  void calculateAverage() {
    float total = 0;
    for(int i = 0; i < intervalMillis.size(); i++) {
      total = total + intervalMillis.get(i);
    }
    avgInterval = total / intervalMillis.size();
    println("avgInterval", avgInterval);
    println(intervalMillis.get(intervalMillis.size() - 1));
  }

  void reset() {
    avgInterval = 1.0;
    IntList zeroList = new IntList();
    intervalMillis = zeroList;
    strobeTime = 0;
  }

  void setStartTime() {
    if(strobeTime == 0) {
      strobeTime = millis();
    }
  }
  
  void pulseLoop() {
   if(pulse == true) {
      if(millis() > strobeTime + avgInterval) {
        spawnEllipse();
        strobeTime = millis();
      }
    }
  }

  void spawnEllipse() {
    ellipse(width/2, height/2, 650, 650);
  }  
}

