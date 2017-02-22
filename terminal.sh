#!/bin/bash

USB_PORT=/dev/cu.usbserial
BAUD_RATE=115200

echo "Starting console session to your Raspberry Pi"
echo "If it fails, please make sure that:"
echo " - your Raspberry Pi has its tty console enabled in the boot options"
echo " - the drivers for your serial TTL cable are installed"
echo " - you have connected a serial TTL cable to the correct GPIO's"
echo
echo "Also, you may have rto press ENTER if the screen stays blank at first"
echo

read  -p "Set USB_PORT? (enter='$USB_PORT'): " OVERRIDE_USB_PORT
[ ! -z $OVERRIDE_USB_PORT ] && USB_PORT=$OVERRIDE_USB_PORT

read -p "Set BAUD_RATE? (enter='$BAUD_RATE'): " OVERRIDE_BAUD_RATE
[ ! -z $OVERRIDE_BAUD_RATE ] && BAUD_RATE=$OVERRIDE_BAUD_RATE

echo "Using $USB_PORT @ $BAUD_RATE"

read -n 1 -p "Press any key to connect"
echo "Connecting..."

echo "screen $USB_PORT $BAUD_RATE"
screen $USB_PORT $BAUD_RATE
