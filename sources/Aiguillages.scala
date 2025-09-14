package myfunlaby

import com.funlabyrinthe.core.*
import com.funlabyrinthe.mazes.*
import com.funlabyrinthe.mazes.std.*

import user.sjrd.railways.*

object Aiguillages extends Module:
  override protected def createComponents()(using Universe): Unit =
    val closedGate = new ClosedGate
    val openGate = new OpenGate
    val closeGateButton = new CloseGateButton
    val openGateButton = new OpenGateButton
  end createComponents

  def closedGate(using Universe): ClosedGate = myComponentByID("closedGate")
  def openGate(using Universe): OpenGate = myComponentByID("openGate")
  def closeGateButton(using Universe): CloseGateButton = myComponentByID("closeGateButton")
  def openGateButton(using Universe): OpenGateButton = myComponentByID("openGateButton")
end Aiguillages

export Aiguillages.*

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
