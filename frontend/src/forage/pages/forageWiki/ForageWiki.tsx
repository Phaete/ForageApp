import {ForageWikiProps} from "./ForageWikiProps.ts"

export default function ForageWiki(props: Readonly<ForageWikiProps>) {
	return (
		<div className={"boxed"}>
			<h2>Forage Wiki</h2>
            {props.forageWikiItems.map(
                forageWikiItem => (
                    <p key={forageWikiItem.name}>{forageWikiItem.name}</p>
                )
            )}
		</div>
	)
}