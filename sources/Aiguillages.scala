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

    val zoneRailsLightCreator = new ZoneRailsLightCreator
  end createComponents

  def closedGate(using Universe): ClosedGate = myComponentByID("closedGate")
  def openGate(using Universe): OpenGate = myComponentByID("openGate")
  def closeGateButton(using Universe): CloseGateButton = myComponentByID("closeGateButton")
  def openGateButton(using Universe): OpenGateButton = myComponentByID("openGateButton")

  def zoneRailsLightCreator(using Universe): ZoneRailsLightCreator = myComponentByID("zoneRailsLightCreator")
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

final class ZoneRailsLightCreator(using ComponentInit) extends ComponentCreator[ZoneRailsLight]:
  category = ComponentCategory("rails", "Rails")

  icon += "Rails/LightOffNorth"
  icon += "Creators/Creator"
end ZoneRailsLightCreator

class ZoneRailsLight(using ComponentInit) extends RailsLight derives Reflector:
  var zoneMap: Option[Map] = None
  var zoneStart: Position = Position.Zero
  var zoneEnd: Position = Position.Zero

  @noinspect
  val updateLightQueue = TimerQueue[Unit] { value =>
    updateLight()
  }

  override def reflect() = autoReflect[ZoneRailsLight]

  def updateLight(): Unit =
    for zoneMap <- this.zoneMap do
      val isZoneOccupied = (zoneStart to zoneEnd).exists { pos =>
        zoneMap.posComponentsBottomUp(pos).exists(_.isInstanceOf[TrainPart])
      }
      isOn = !isZoneOccupied

      updateLightQueue.schedule(250, ())
  end updateLight

  override def startGame(): Unit =
    updateLight()
end ZoneRailsLight
