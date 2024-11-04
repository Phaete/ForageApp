import {ContentProps} from "./ContentProps.ts"

export default function Content(props: Readonly<ContentProps>) {
	return (
		<div className={"flex align-center flex-col"}>
			{props.children}
		</div>
	)
}