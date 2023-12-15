import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

/**
 * The Sketch class is the main class for the Processing sketch.
 */
public class Sketch extends PApplet {

  // Image Variables
  PImage imgDungeon;
  PImage imgStaff;
  PImage imgSword1;
  PImage imgSword2;
  PImage[] imgSword1Attacks;
  PImage[] imgSword2Attacks;
  PImage[] imgStaffAttacks;
  PImage imgTeemo;
  PImage imgShroom;

  ArrayList<float[]> mushrooms = new ArrayList<float[]>();

  // Weapon Selection Animation Variables
  int intSword1X = 18;
  int intSword1Y = 370;

  int intSword2X = 260;
  int intSword2Y = 400;

  int intStaffX = 470;
  int intStaffY = 355;

  int intTeemoX = 400, intTeemoY = 490;

  int intWeaponSpeed = 10;

  int intWeaponSelect;

  // Time interval for drawing grass
  int intInterval = 100;
  // Last time the grass was drawn
  int intLastTime = 0;

  boolean blnLocked = false;
  boolean wKey, aKey, sKey, dKey;

  float speed = 5;

  /**
   * Specifies the size of the sketch.
   */
  public void settings() {
    size(600, 600);
  }

  /**
   * Loads images and performs initial setup.
   */
  public void setup() {
    imgDungeon = loadImage("dungeon.jpg");
    imgDungeon.resize(600, 600);

    imgSword1 = loadImage("sword1.png");
    imgSword1.resize(150, 200);

    imgSword2 = loadImage("sword2.png");
    imgSword2.resize(150, 200);

    imgStaff = loadImage("staff3.png");
    imgStaff.resize(150, 200);

    imgSword1 = loadImage("sword1.png");
    imgSword2 = loadImage("sword2.png");
    imgStaff = loadImage("staff3.png");

    imgShroom = loadImage("teemoShroom.png");
    imgShroom.resize(20, 20);

    imgTeemo = loadImage("teemo.png");
    imgTeemo.resize(100, 100);

    imgSword1Attacks = new PImage[4];
    for (int i = 0; i < imgSword1Attacks.length; i++) {
      imgSword1Attacks[i] = loadImage("sword1attack" + i + ".png");
    }

    imgSword2Attacks = new PImage[4];
    for (int i = 0; i < imgSword2Attacks.length; i++) {
      imgSword2Attacks[i] = loadImage("sword2attack" + i + ".png");
    }

    imgStaffAttacks = new PImage[4];
    for (int i = 0; i < imgStaffAttacks.length; i++) {
      imgStaffAttacks[i] = loadImage("imgStaffAttack" + i + ".png");
    }
  }

  /**
   * Main draw loop, updates and renders the sketch.
   */
  public void draw() {
    image(imgDungeon, 0, 0);
    if (!blnLocked) {
      animateWeaponSelection();
    } else {
      displaySelected();
      if (mousePressed) {
        attack();
      }
    }
    for (float[] mushroom : mushrooms) {
      image(imgShroom, mushroom[0], mushroom[1]);
    }
    drawTeemo();
    teemoMovement();
  }

  /**
   * Draws the Teemo character.
   */
  public void drawTeemo() {
    image(imgTeemo, intTeemoX, intTeemoY);
  }

  /**
   * Handles Teemo character movement based on key input.
   */
  public void teemoMovement() {
    if (wKey) {
      if (intTeemoY - speed >= 0) {
        intTeemoY -= speed;
      }
    }
    if (sKey) {
      if (intTeemoY + speed <= height - imgTeemo.height) {
        intTeemoY += speed;
      }
    }
    if (aKey) {
      if (intTeemoX - speed > 0) {
        intTeemoX -= speed;
      }
    }
    if (dKey) {
      if (intTeemoX + speed < width - imgTeemo.width) {
        intTeemoX += speed;
      }
    }
  }

  /**
   * Handles key presses for movement and locking/unlocking.
   */
  public void keyPressed() {
    if (key == 'w' || key == 'W') {
      wKey = true;
    } else if (key == 's' || key == 'S') {
      sKey = true;
    } else if (key == 'a' || key == 'A') {
      aKey = true;
    } else if (key == 'd' || key == 'D') {
      dKey = true;
    }
    if (key == 'o' || key == 'O') {
      blnLocked = !blnLocked;
    }
  }

  /**
   * Handles key releases for movement.
   */
  public void keyReleased() {
    if (key == 'w' || key == 'W') {
      wKey = false;
    } else if (key == 's' || key == 'S') {
      sKey = false;
    } else if (key == 'a' || key == 'A') {
      aKey = false;
    } else if (key == 'd' || key == 'D') {
      dKey = false;
    }
  }

  /**
   * Handles the attack logic when the mouse is pressed.
   */
  public void attack() {
    if (intWeaponSelect == 1 && frameCount % 5 == 0) {
      int index = frameCount / 5 % imgSword1Attacks.length;
      image(imgSword1Attacks[index], mouseX - imgStaffAttacks[index].width / 2, mouseY - imgSword1Attacks[index].height / 2);
    } else if (intWeaponSelect == 2 && frameCount % 5 == 0) {
      int index = frameCount / 5 % imgSword1Attacks.length;
      image(imgSword2Attacks[index], mouseX - imgStaffAttacks[index].width / 2, mouseY - imgSword2Attacks[index].height / 2);
    } else if (intWeaponSelect == 3 && frameCount % 5 == 0) {
      int index = frameCount / 5 % imgSword1Attacks.length;
      image(imgStaffAttacks[index], mouseX - imgStaffAttacks[index].width / 2, mouseY - imgStaffAttacks[index].height / 2);
    }
  }

  /**
   * Handles the drawing of mushrooms when the mouse is dragged.
   */
  public void mouseDragged() {
    if (mouseY > height / 2) {
      if (millis() - intLastTime > intInterval) {
        float[] mushroom = { mouseX - 10, mouseY - 10 };
        mushrooms.add(mushroom);
        intLastTime = millis();
      }
    }
  }

  /**
   * Animates the weapon selection based on key input.
   */
  public void animateWeaponSelection() {
    if (keyPressed) {
      if (keyCode == LEFT) {
        intWeaponSelect -= 1;
        delay(80);
        if (intWeaponSelect < 1) {
          intWeaponSelect = 3;
        }
      } else if (keyCode == RIGHT) {
        intWeaponSelect += 1;
        delay(80);
        if (intWeaponSelect > 3) {
          intWeaponSelect = 1;
        }
      }
    }

    if (intWeaponSelect == 1) {
      if (frameCount % 5 == 0) {
        if (intSword1Y < 370 + 20) {
          intSword1Y += intWeaponSpeed;
        } else if (intSword1Y > 370 - 20) {
          intSword1Y -= intWeaponSpeed;
        }
        intSword1Y = constrain(intSword1Y, 370 - 20, 370 + 20);
      }
    } else if (intWeaponSelect == 2) {
      if (frameCount % 5 == 0) {
        if (intSword2Y < 400 + 20) {
          intSword2Y += intWeaponSpeed;
        } else if (intSword2Y > 400 - 20) {
          intSword2Y -= intWeaponSpeed;
        }
        intSword2Y = constrain(intSword2Y, 400 - 20, 400 + 20);
      }
    } else if (intWeaponSelect == 3) {
      if (frameCount % 5 == 0) {
        if (intStaffY < 355 + 20) {
          intStaffY += intWeaponSpeed;
        } else if (intStaffY > 355 - 20) {
          intStaffY -= intWeaponSpeed;
        }
        intStaffY = constrain(intStaffY, 355 - 20, 355 + 20);
      }
    }

    if (intWeaponSelect == 1) {
      image(imgSword1, intSword1X, intSword1Y);
    } else if (intWeaponSelect == 2) {
      image(imgSword2, intSword2X, intSword2Y);
    } else if (intWeaponSelect == 3) {
      image(imgStaff, intStaffX, intStaffY);
    } else {

    }
  }

  /**
   * Displays the selected weapon in the center of the canvas.
   */
  public void displaySelected() {
    if (intWeaponSelect == 1) {
      image(imgSword1, width / 2 - imgSword1.width / 2, height / 2 - imgSword1.height / 2);
    } else if (intWeaponSelect == 2) {
      image(imgSword2, width / 2 - imgSword2.width / 2, height / 2 - imgSword2.height / 2);
    } else if (intWeaponSelect == 3) {
      image(imgStaff, width / 2 - imgStaff.width / 2, height / 2 - imgStaff.height / 2);
    } else {

    }
  }
}
