import '../../index-out.css'
import React from 'react'
import './igPostCard.css'
import { InstagramEmbed } from 'react-social-media-embed';


function IgPostCard() {



    return <>
        <div className='igpost-card bg-white rounded-md border border-gray-100 p-6 shadow-md shadow-black'>
            <div className='igpost-card actual-post'>
                <InstagramEmbed url="https://www.instagram.com/p/C6W0p8Art0q/" width={330} captioned={false} />
            </div>
        </div>
    </>
}

export default IgPostCard