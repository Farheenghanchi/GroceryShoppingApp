package com.farheen.grossary.lottie_anim;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.farheen.grossary.MainActivity;
import com.farheen.grossary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottieFontViewGroup extends FrameLayout {
  private final Map<String, LottieComposition> compositionMap = new HashMap<>();
  private final List<View> views = new ArrayList<>();
    String name = "FARHA ";
    int count = 0;
    int size = 120;

    Context mContext;
  @Nullable private LottieAnimationView cursorView;

  public LottieFontViewGroup(Context context) {
    super(context);
    init(context);
  }

  public LottieFontViewGroup(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public LottieFontViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context mContext) {
      this.mContext = mContext;
//    setFocusableInTouchMode(true);

//      final String fileName = letter + ".json";

      myAlphabet(name.charAt(count));
      count++;
  }

  private void myAlphabet(char c){
      if(c != ' '){
          final String filename = c+".json";
          LottieComposition.Factory.fromAssetFileName(getContext(), filename,
                  new OnCompositionLoadedListener() {
                      @Override
                      public void onCompositionLoaded(LottieComposition composition) {
                          cursorView = new LottieAnimationView(getContext());
                          cursorView.setLayoutParams(new LayoutParams(
                                  size,size
                          ));
                          cursorView.setComposition(composition);
//                          cursorView.loop(true);
                          cursorView.playAnimation();
                          addView(cursorView);

                          if(name.charAt(count) == 'R'){
                              myAlphabet(name.charAt(count));
                              count++;
                          }else {
                              new Handler().postDelayed(new Runnable() {
                                  @Override
                                  public void run() {
                                      myAlphabet(name.charAt(count));
                                      count++;
                                  }
                              },500);
                          }

                      }
                  });
          /*if (compositionMap.containsKey(filename)) {
              addComposition(compositionMap.get(filename));
          } else {
              LottieComposition.Factory.fromAssetFileName(getContext(), filename,
                      new OnCompositionLoadedListener() {
                          @Override
                          public void onCompositionLoaded(LottieComposition composition) {
                              compositionMap.put(filename, composition);
                              addComposition(composition);

                              new Handler().postDelayed(new Runnable() {
                                  @Override
                                  public void run() {
                                      myAlphabet(name.charAt(count));
                                      count++;
                                  }
                              },100);

                          }
                      });
          }*/
      }else {
          LottieComposition.Factory.fromAssetFileName(getContext(), "BlinkingCursor.json",
              new OnCompositionLoadedListener() {
                  @Override
                  public void onCompositionLoaded(LottieComposition composition) {
                      cursorView = new LottieAnimationView(getContext());
                      cursorView.setLayoutParams(new LayoutParams(
                              50,size
                      ));
                      cursorView.setComposition(composition);
                      cursorView.loop(true);
                      cursorView.setSpeed((float)1.15);
                      cursorView.playAnimation();
                      addView(cursorView);

                      new Handler().postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              Intent i = new Intent(mContext, MainActivity.class);
                              mContext.startActivity(i);


                          }
                      },1500);
                  }
              });
      }
  }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        if (index == -1) {
            views.add(child);
        } else {
            views.add(index, child);
        }
    }
 private void addSpace() {
    int index = indexOfChild(cursorView);
    addView(createSpaceView(), index);
  }



    private View createSpaceView() {
        View spaceView = new View(getContext());
        spaceView.setLayoutParams(new LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.font_space_width),
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        spaceView.setTag("Space");
        return spaceView;
    }
  private void removeLastView() {
    if (views.size() > 1) {
      int position = views.size() - 2;
      removeView(views.get(position));
      views.remove(position);
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    if (views.isEmpty()) {
      return;
    }
    int currentX = getPaddingTop();
    int currentY = getPaddingLeft();

    for (int i = 0; i < views.size(); i++) {
      View view = views.get(i);
      if (!fitsOnCurrentLine(currentX, view)) {
        if (view.getTag() != null && view.getTag().equals("Space")) {
          continue;
        }
        currentX = getPaddingLeft();
        currentY += view.getMeasuredHeight();
      }
      currentX += view.getWidth();
    }

    setMeasuredDimension(getMeasuredWidth(),
        currentY + views.get(views.size() - 1).getMeasuredHeight() * 2);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    if (views.isEmpty()) {
      return;
    }
    int currentX = getPaddingTop();
    int currentY = getPaddingLeft();

    for (int i = 0; i < views.size(); i++) {
      View view = views.get(i);
      if (!fitsOnCurrentLine(currentX, view)) {
        if (view.getTag() != null && view.getTag().equals("Space")) {
          continue;
        }
        currentX = getPaddingLeft();
        currentY += view.getMeasuredHeight();
      }
      view.layout(currentX, currentY, currentX + view.getMeasuredWidth(),
          currentY + view.getMeasuredHeight());
      currentX += view.getWidth();
    }
  }

  @Override
  public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
    BaseInputConnection fic = new BaseInputConnection(this, false);
    outAttrs.actionLabel = null;
    outAttrs.inputType = InputType.TYPE_NULL;
    outAttrs.imeOptions = EditorInfo.IME_ACTION_NEXT;
    return fic;
  }

  @Override
  public boolean onCheckIsTextEditor() {
    return true;
  }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_SPACE) {
            addSpace();
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_DEL) {
            removeLastView();
            return true;
        }

        if (!isValidKey(event)) {
            return super.onKeyUp(keyCode, event);
        }


        String letter = "" + Character.toUpperCase((char) event.getUnicodeChar());
        // switch (letter) {
        //     case ",":
        //         letter = "Comma";
        //         break;
        //     case "'":
        //         letter = "Apostrophe";
        //         break;
        //     case ";":
        //     case ":":
        //         letter = "Colon";
        //         break;
        // }
        final String fileName = letter + ".json";
        if (compositionMap.containsKey(fileName)) {
            addComposition(compositionMap.get(fileName));
        } else {
            LottieComposition.Factory.fromAssetFileName(getContext(), fileName,
                    new OnCompositionLoadedListener() {
                        @Override
                        public void onCompositionLoaded(LottieComposition composition) {
                            compositionMap.put(fileName, composition);
                            addComposition(composition);
                        }
                    });
        }
        return true;
    }

  private boolean isValidKey(KeyEvent event) {
    if (!event.hasNoModifiers()) {
      return false;
    }
    if (event.getKeyCode() >= KeyEvent.KEYCODE_A && event.getKeyCode() <= KeyEvent.KEYCODE_Z) {
      return true;
    }

    // switch (keyCode) {
    //     case KeyEvent.KEYCODE_COMMA:
    //     case KeyEvent.KEYCODE_APOSTROPHE:
    //     case KeyEvent.KEYCODE_SEMICOLON:
    //         return true;
    // }
    return false;
  }

  private void addComposition(LottieComposition composition) {
    LottieAnimationView lottieAnimationView = new LottieAnimationView(getContext());
    lottieAnimationView.setLayoutParams(new LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    ));
    lottieAnimationView.setComposition(composition);
    lottieAnimationView.playAnimation();
    if (cursorView == null) {
      addView(lottieAnimationView);
    } else {
      int index = indexOfChild(cursorView);
      addView(lottieAnimationView, index);
    }
  }

  private boolean fitsOnCurrentLine(int currentX, View view) {
    return currentX + view.getMeasuredWidth() < getWidth() - getPaddingRight();
  }


}
