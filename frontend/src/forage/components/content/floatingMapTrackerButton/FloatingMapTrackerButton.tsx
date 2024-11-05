import {FloatingMapTrackerButtonProps} from "./FloatingMapTrackerButtonProps.ts"

export default function FloatingMapTrackerButton(props: Readonly<FloatingMapTrackerButtonProps>) {
	return (
		<div className={"floating-box bottom-5 right-5"}>
			{props.requestTracking ?
				<button type={"button"} onClick={() => props.handleTrackingToggle()}
						className={`flex align-center justify-center no-text-deco button ${props.trackingAllowed ? 'tracking-allowed' : 'tracking-denied'} bg-white`}>
				</button>
				:
				<button type={"button"} onClick={() => props.setRequestTracking(true)}
						className={"flex align-center justify-center no-text-deco button locate-me bg-white"}>
				</button>
			}
		</div>
	)
}