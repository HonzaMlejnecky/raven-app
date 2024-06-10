import '../../index-out.css'
import React from 'react'
import './igPostCard.css'
import { InstagramEmbed } from 'react-social-media-embed';
import { useEffect } from 'react';


function IgPostCard(props: { shortcode: string }) {

    const [reload, setReload] = React.useState(true);

    useEffect(() => {
        setReload(!reload);
    }, [props.shortcode])

    return <>
        <div className='igpost-card bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black'>
            <div className='igpost-card actual-post'>
                { reload ? <InstagramEmbed url={`https://www.instagram.com/p/${props.shortcode}/`} width={330} captioned={false} />
                 : <></>}
                 {
                    !reload ? <InstagramEmbed url={`https://www.instagram.com/p/${props.shortcode}/`} width={330} captioned={false} />
                    : <></>
                 }
            </div>
        </div>
    </>
}

export default IgPostCard
