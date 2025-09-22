package myfunlaby

import com.funlabyrinthe.core.*
import com.funlabyrinthe.mazes.*
import com.funlabyrinthe.mazes.std.*

import user.sjrd.railways.*

object Aiguillages extends Module

@definition def closedGate(using Universe) = new ClosedGate
@definition def openGate(using Universe) = new OpenGate
@definition def closeGateButton(using Universe) = new CloseGateButton
@definition def openGateButton(using Universe) = new OpenGateButton

class ClosedGate(using ComponentInit) extends Obstacle:
  painter += "Gates/ClosedPorch"
end ClosedGate

class OpenGate(using ComponentInit) extends Effect:
  painter += "Gates/OpenPorch"
end OpenGate

class CloseGateButton(using ComponentInit) extends PushButton:
  override def buttonDown(context: MoveContext): Unit =
    import context.*
    pos.map(10, 0, 0) = grass + closedGate
end CloseGateButton

class OpenGateButton(using ComponentInit) extends PushButton:
  override def buttonDown(context: MoveContext): Unit =
    import context.*
    pos.map(10, 0, 0) = grass + openGate
end OpenGateButton
