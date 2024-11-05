export type FloatingMapTrackerButtonProps = {
	requestTracking: boolean,
	handleTrackingToggle: () => void,
	trackingAllowed: boolean,
	setRequestTracking: (requestTracking: boolean) => void
}