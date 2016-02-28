package shrey.gosporto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardArrayAdapter extends ArrayAdapter<Card> {
    private static final String TAG = "CardArrayAdapter";
    private List<Card> cardList = new ArrayList<Card>();

    static class CardViewHolder {
        TextView line1;
        TextView line2;
        TextView line3;
        TextView line4;
        ImageView pic;
    }

    public CardArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(Card object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Card getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.line1 = (TextView) row.findViewById(R.id.line1);
            viewHolder.line2 = (TextView) row.findViewById(R.id.line2);
            viewHolder.line3 = (TextView) row.findViewById(R.id.line3);
            viewHolder.line4 = (TextView) row.findViewById(R.id.line4);
            viewHolder.pic = (ImageView) row.findViewById(R.id.pic);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        Card card = getItem(position);
        viewHolder.line1.setText(card.getTitle());
        viewHolder.line3.setText("INTENSITY :" + card.getVenue());
        viewHolder.line2.setText("TIME : " + card.getTime());
        viewHolder.line4.setText("DISTANCE : " + card.getCost()+ " metres    Calories : " + card.getSport_id() + " cal");
        String s = card.getSport_id();
//        if(s.equals("1"))
//            viewHolder.pic.setImageResource(R.drawable.colorcricket);
//        if(s.equals("2"))
//            viewHolder.pic.setImageResource(R.drawable.colorfootball);
//        if(s.equals("3"))
//            viewHolder.pic.setImageResource(R.drawable.colorbasketball);
//        if(s.equals("12"))
//            viewHolder.pic.setImageResource(R.drawable.colorcricket);
//        if(s.equals("5"))
//            viewHolder.pic.setImageResource(R.drawable.colortennis);
//        if(s.equals("6"))
//            viewHolder.pic.setImageResource(R.drawable.colorsquash);
//        if(s.equals("7"))
//            viewHolder.pic.setImageResource(R.drawable.colorcricket);
//        if(s.equals("8"))
//            viewHolder.pic.setImageResource(R.drawable.colortt);
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
